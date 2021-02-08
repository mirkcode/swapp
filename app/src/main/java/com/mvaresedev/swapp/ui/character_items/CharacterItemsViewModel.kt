package com.mvaresedev.swapp.ui.character_items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.repo.StarWarsRepository
import com.mvaresedev.swapp.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CharacterItemsViewModel(
    private val repository: StarWarsRepository
) : ViewModel() {

    val loadStateChannel = Channel<CombinedLoadStates>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            loadStateChannel.consumeAsFlow().collect { loadState ->
                handleLoadStateChange(loadState)
            }
        }
    }

    val navigationToDetail by lazy { MutableLiveData<Event<Character>>() }
    val loadingVisible by lazy { MutableLiveData<Boolean>() }
    val errorVisible by lazy { MutableLiveData<Boolean>() }

    fun onCharacterItemClick(character: Character) {
        navigationToDetail.postValue(Event(character))
    }

    private fun handleLoadStateChange(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                errorVisible.postValue(false)
                loadingVisible.postValue(true)
            }
            is LoadState.Error -> {
                errorVisible.postValue(true)
                loadingVisible.postValue(false)
            }
            else -> {
                errorVisible.postValue(false)
                loadingVisible.postValue(false)
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val charactersFlow = repository.retrieveCharacters().cachedIn(viewModelScope)


}