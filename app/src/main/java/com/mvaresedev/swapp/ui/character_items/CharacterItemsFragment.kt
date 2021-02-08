package com.mvaresedev.swapp.ui.character_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.swapp.databinding.FragmentCharacterItemsBinding
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.ui.character_items.CharacterPagingAdapter.Companion.LOADING_VIEW_TYPE
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharacterItemsFragment : Fragment() {

    private val args: CharacterItemsFragmentArgs by navArgs()

    private val viewModel by viewModel<CharacterItemsViewModel>()
    private val adapter by lazy { CharacterPagingAdapter(::onItemCLick, args.viewType) }

    private var _binding: FragmentCharacterItemsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewModel()
    }

    private fun setupUi() {
        when(args.viewType) {
            ViewStyle.GRID -> {
                val gridLayoutManager = GridLayoutManager(requireContext(), 2)
                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if(adapter.getItemViewType(position) == LOADING_VIEW_TYPE) 2 else 1
                    }
                }
                binding.characterRv.layoutManager = gridLayoutManager
            }
            ViewStyle.LIST -> {
                binding.characterRv.layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                )
            }
        }
        binding.characterRv.adapter = adapter.withLoadStateFooter(
            footer = CharacterLoadingAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            lifecycleScope.launch {
                viewModel.loadStateChannel.send(loadState)
            }
        }
        binding.retryBtn.setOnClickListener {
            adapter.retry()
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            lifecycleScope.launchWhenCreated {
                charactersFlow.collectLatest {
                    adapter.submitData(it)
                }
            }
            navigationToDetail.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandledOrReturnNull()?.let { character ->
                    val action = CharacterItemsFragmentDirections.actionCharacterDetail(character)
                    findNavController().navigate(action)
                }
            })
            loadingVisible.observe(viewLifecycleOwner, { visible ->
                binding.loadingContainer.isVisible = visible
            })
            errorVisible.observe(viewLifecycleOwner, { visible ->
                binding.errorContainer.isVisible = visible
            })
        }
    }

    private fun onItemCLick(character: Character) {
        lifecycleScope.launch {
            viewModel.onCharacterItemClick(character)
        }
    }
}