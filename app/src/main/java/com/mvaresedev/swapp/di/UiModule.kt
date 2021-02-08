package com.mvaresedev.swapp.di

import com.mvaresedev.swapp.ui.character_detail.CharacterDetailViewModel
import com.mvaresedev.swapp.ui.character_items.CharacterItemsViewModel
import com.mvaresedev.swapp.ui.select_visualization.SelectVisualizationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { SelectVisualizationViewModel() }
    viewModel { CharacterItemsViewModel(get()) }
    viewModel { CharacterDetailViewModel(get()) }
}
