package com.mvaresedev.swapp.data.network

import com.mvaresedev.swapp.data.network.models.FilmDetailResponse
import com.mvaresedev.swapp.data.network.models.PeopleListResponse
import com.mvaresedev.swapp.data.network.models.VehicleDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface StarWarsApi {

    @GET
    suspend fun getPeopleListByUrl(@Url url: String): PeopleListResponse

    @GET("films/{id}/")
    suspend fun getFilmDetail(
        @Path("id") id: Int
    ): FilmDetailResponse

    @GET("vehicles/{id}/")
    suspend fun getVehicleDetail(
        @Path("id") id: Int
    ): VehicleDetailResponse

}