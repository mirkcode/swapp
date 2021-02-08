package com.mvaresedev.swapp.domain.mapper

import com.mvaresedev.swapp.data.network.models.FilmDetailResponse
import com.mvaresedev.swapp.data.network.models.PeopleListResponse
import com.mvaresedev.swapp.data.network.models.VehicleDetailResponse
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle

interface NetworkMapper {
    fun mapListResponseToDomain(listResponse: PeopleListResponse): List<Character>
    fun mapFilmResponsesToDomain(filmResponses: List<FilmDetailResponse>): List<Film>
    fun mapVehicleResponsesToDomain(vehicleResponse: List<VehicleDetailResponse>): List<Vehicle>
}