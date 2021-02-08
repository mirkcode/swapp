package com.mvaresedev.swapp.ui.select_visualization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvaresedev.swapp.databinding.FragmentSelectVisualizationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectVisualizationFragment : Fragment() {

    private var _binding: FragmentSelectVisualizationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SelectVisualizationViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectVisualizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.gridBtn.setOnClickListener {
            viewModel.onGridClicked()
        }
        binding.listBtn.setOnClickListener {
            viewModel.onListClicked()
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            navigation.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandledOrReturnNull()?.let { viewStyle ->
                    val action = SelectVisualizationFragmentDirections.actionCharacterItems(viewStyle)
                    findNavController().navigate(action)
                }
            })
        }
    }
}