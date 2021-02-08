package com.mvaresedev.swapp.domain.repo

import androidx.paging.PagingData
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle
import com.mvaresedev.swapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    fun retrieveCharacters(): Flow<PagingData<Character>>
    suspend fun retrieveFilms(filmIds: List<Int>): ResultWrapper<List<Film>>
    suspend fun retrieveVehicles(vehicleIds: List<Int>): ResultWrapper<List<Vehicle>>
}