package com.mvaresedev.swapp.ui.select_visualization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvaresedev.swapp.ui.character_items.ViewStyle
import com.mvaresedev.swapp.utils.Event

class SelectVisualizationViewModel : ViewModel() {

    val navigation by lazy { MutableLiveData<Event<ViewStyle>>() }

    fun onGridClicked() {
        navigation.postValue(Event(ViewStyle.GRID))
    }

    fun onListClicked() {
        navigation.postValue(Event(ViewStyle.LIST))
    }

}