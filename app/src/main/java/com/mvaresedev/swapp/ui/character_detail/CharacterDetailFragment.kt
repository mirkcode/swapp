package com.mvaresedev.swapp.ui.character_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.binaryfork.spanny.Spanny
import com.bumptech.glide.Glide
import com.mvaresedev.swapp.R
import com.mvaresedev.swapp.databinding.FragmentCharacterDetailBinding
import com.mvaresedev.swapp.utils.CustomTypefaceSpan
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private val args: CharacterDetailFragmentArgs by navArgs()

    private val viewModel by viewModel<CharacterDetailViewModel>()

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val filmAdapter by lazy { FilmAdapter() }
    private val vehicleAdapter by lazy { VehicleAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
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

        lifecycleScope.launch {
            viewModel.initCharacter(args.character)
        }
    }

    private fun setupUi() {
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.filmsRv.adapter = filmAdapter
        binding.vehiclesRv.adapter = vehicleAdapter
        binding.retryFilmBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onRetryFilmClicked()
            }
        }
        binding.retryVehicleBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onRetryVehiclesClicked()
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            title.observe(viewLifecycleOwner, { title ->
                binding.toolbarTitleTxt.text = title
            })
            avatarUrl.observe(viewLifecycleOwner, { avatarUrl ->
                Glide.with(requireContext()).load(avatarUrl).placeholder(R.drawable.ic_placeholder).centerCrop().into(binding.avatarImg)
            })
            filmList.observe(viewLifecycleOwner, { films ->
                filmAdapter.submitData(films)
            })
            vehicleList.observe(viewLifecycleOwner, { films ->
                vehicleAdapter.submitData(films)
            })
            height.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.heightTxt, pair.first, pair.second)
            })
            weight.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.weightTxt, pair.first, pair.second)
            })
            gender.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.genderTxt, pair.first, pair.second)
            })
            hairColor.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.hairColorTxt, pair.first, pair.second)
            })
            skinColor.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.skinColorTxt, pair.first, pair.second)
            })
            birthYear.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.birthYearTxt, pair.first, pair.second)
            })
            eyeColor.observe(viewLifecycleOwner, { pair ->
                setSpannedText(binding.eyeColorTxt, pair.first, pair.second)
            })
            genderVisible.observe(viewLifecycleOwner, { visible ->
                binding.genderTxt.isVisible = visible
            })
            eyeColorVisible.observe(viewLifecycleOwner, { visible ->
                binding.eyeColorTxt.isVisible = visible
            })
            hairColorVisible.observe(viewLifecycleOwner, { visible ->
                binding.hairColorTxt.isVisible = visible
            })
            noVehicleVisible.observe(viewLifecycleOwner, { visible ->
                binding.noVehiclesTxt.isVisible = visible
            })
            filmProgressVisible.observe(viewLifecycleOwner, { visible ->
                binding.filmsPb.isVisible = visible
            })
            vehicleProgressVisible.observe(viewLifecycleOwner, { visible ->
                binding.vehiclesPb.isVisible = visible
            })
            vehicleErrorVisible.observe(viewLifecycleOwner, { visible ->
                binding.vehicleErrorLayout.isVisible = visible
            })
            filmErrorVisible.observe(viewLifecycleOwner, { visible ->
                binding.filmErrorLayout.isVisible = visible
            })
        }
    }

    private fun setSpannedText(textView: TextView, @StringRes labelRes: Int, text: String) {
        textView.text = Spanny(getString(labelRes)).append(" ").append(text, CustomTypefaceSpan(ResourcesCompat.getFont(requireContext(), R.font.proxima_nova_thin)))
    }
}