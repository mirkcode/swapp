package com.mvaresedev.swapp.data.network.mapper

import com.mvaresedev.swapp.data.network.models.FilmDetailResponse
import com.mvaresedev.swapp.data.network.models.PeopleListResponse
import com.mvaresedev.swapp.data.network.models.VehicleDetailResponse
import com.mvaresedev.swapp.domain.mapper.NetworkMapper
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle
import java.text.SimpleDateFormat
import java.util.*

class NetworkMapperImpl : NetworkMapper {

    override fun mapListResponseToDomain(listResponse: PeopleListResponse): List<Character> {
        return listResponse.results.map { item ->
            with(item) {
                val id = retrievePeopleIdFromUrl(url)
                Character(
                    id,
                    birthYear,
                    parseNotAvailableField(eyeColor),
                    films.map { url -> retrieveFilmIdFromUrl(url) },
                    parseNotAvailableField(gender),
                    parseNotAvailableField(hairColor),
                    height,
                    mass,
                    name,
                    skinColor,
                    vehicles.map { url -> retrieveVehicleIdFromUrl(url) },
                    retrieveAvatarUrl(id)
                )
            }
        }
    }

    private fun parseNotAvailableField(field: String): String? {
        return if(field.equals("n/a", true))
            null
        else
            field
    }

    override fun mapFilmResponsesToDomain(filmResponses: List<FilmDetailResponse>): List<Film> {
        return filmResponses.map { item ->
            with(item) {
                val id = retrieveFilmIdFromUrl(item.url)
                Film(id, title, parseFilmYear(releaseDate), openingCrawl)
            }
        }
    }

    override fun mapVehicleResponsesToDomain(vehicleResponse: List<VehicleDetailResponse>): List<Vehicle> {
        return vehicleResponse.map { item ->
            with(item) {
                val id = retrieveVehicleIdFromUrl(item.url)
                Vehicle(id, name, model, vehicleClass, manufacturer, "$length m", costInCredits, maxAtmospheringSpeed)
            }
        }
    }

    private fun retrievePeopleIdFromUrl(url: String): Int {
        val splitted = url.split("people/")
        val idString = splitted.last().substring(0, splitted.last().indexOfLast { it == '/' })
        return idString.toInt()
    }

    private fun retrieveFilmIdFromUrl(url: String): Int {
        val splitted = url.split("films/")
        val idString = splitted.last().substring(0, splitted.last().indexOfLast { it == '/' })
        return idString.toInt()
    }

    private fun retrieveVehicleIdFromUrl(url: String): Int {
        val splitted = url.split("vehicles/")
        val idString = splitted.last().substring(0, splitted.last().indexOfLast { it == '/' })
        return idString.toInt()
    }

    private fun retrieveAvatarUrl(id: Int): String {
        return "https://mobile.aws.skylabs.it/mobileassignments/swapi/$id.png"
    }

    private val releaseDateDf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    private fun parseFilmYear(releaseDate: String): String {
        val dateParsed = releaseDateDf.parse(releaseDate)
        return if(dateParsed != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = dateParsed.time
            calendar.get(Calendar.YEAR).toString()
        } else {
            ""
        }
    }
}