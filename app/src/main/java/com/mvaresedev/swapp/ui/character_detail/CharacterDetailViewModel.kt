package com.mvaresedev.swapp.ui.character_detail

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvaresedev.swapp.R
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle
import com.mvaresedev.swapp.domain.repo.StarWarsRepository
import com.mvaresedev.swapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class CharacterDetailViewModel(
    private val repository: StarWarsRepository
): ViewModel() {

    private lateinit var character: Character

    val title by lazy { MutableLiveData<String>() }
    val height by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val weight by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val gender by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val birthYear by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val eyeColor by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val hairColor by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val skinColor by lazy { MutableLiveData<Pair<@StringRes Int,String>>() }
    val genderVisible by lazy { MutableLiveData<Boolean>() }
    val hairColorVisible by lazy { MutableLiveData<Boolean>() }
    val eyeColorVisible by lazy { MutableLiveData<Boolean>() }
    val filmProgressVisible by lazy { MutableLiveData<Boolean>() }
    val filmErrorVisible by lazy { MutableLiveData<Boolean>() }
    val vehicleProgressVisible by lazy { MutableLiveData<Boolean>() }
    val noVehicleVisible by lazy { MutableLiveData<Boolean>() }
    val vehicleErrorVisible by lazy { MutableLiveData<Boolean>() }
    val avatarUrl by lazy { MutableLiveData<String>() }
    val filmList by lazy { MutableLiveData<List<Film>>() }
    val vehicleList by lazy { MutableLiveData<List<Vehicle>>() }

    suspend fun initCharacter(character: Character) {

        this.character = character

        title.postValue(character.name)
        avatarUrl.postValue(character.avatarUrl)

        populateCharacterDetail(character)

        withContext(Dispatchers.Main) {
            val films = async { getFilmList(character.films) }
            val vehicles = async { getVehicleList(character.vehicles) }

            films.await()
            vehicles.await()
        }
    }

    private suspend fun getFilmList(films: List<Int>) {
        filmErrorVisible.postValue(false)
        filmProgressVisible.postValue(true)

        val filmWrapper = repository.retrieveFilms(films)
        filmProgressVisible.postValue(false)
        if(filmWrapper.result.isNotEmpty())
            filmList.postValue(filmWrapper.result)
        else if(filmWrapper is ResultWrapper.Error)
            filmErrorVisible.postValue(true)
    }

    private suspend fun getVehicleList(vehicles: List<Int>) {

        noVehicleVisible.postValue(false)
        vehicleErrorVisible.postValue(false)

        vehicleProgressVisible.postValue(true)
        val vehicleWrapper = repository.retrieveVehicles(vehicles)
        vehicleProgressVisible.postValue(false)
        if(vehicleWrapper.result.isNotEmpty()) {
            vehicleList.postValue(vehicleWrapper.result)
        } else {
            if(vehicleWrapper is ResultWrapper.Error)
                vehicleErrorVisible.postValue(true)
            else
                noVehicleVisible.postValue(true)
        }
    }

    private fun populateCharacterDetail(character: Character) {
        weight.postValue(R.string.detail_weight to "${character.mass} kg")
        height.postValue(R.string.detail_height to "${character.height} cm")
        birthYear.postValue(R.string.detail_birth_year to character.birthYear)
        skinColor.postValue(R.string.detail_skin_color to character.skinColor)

        if(character.gender != null) {
            gender.postValue(R.string.detail_gender to character.gender)
            genderVisible.postValue(true)
        } else {
            genderVisible.postValue(false)
        }

        if(character.eyeColor != null) {
            eyeColor.postValue(R.string.detail_eye_color to character.eyeColor)
            eyeColorVisible.postValue(true)
        } else {
            eyeColorVisible.postValue(false)
        }
        if(character.hairColor != null) {
            hairColor.postValue(R.string.detail_hair_color to character.hairColor)
            hairColorVisible.postValue(true)
        } else {
            hairColorVisible.postValue(false)
        }
    }

    suspend fun onRetryFilmClicked() {
        getFilmList(character.films)
    }

    suspend fun onRetryVehiclesClicked() {
        getVehicleList(character.vehicles)
    }

}