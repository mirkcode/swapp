package com.mvaresedev.swapp.data.db.mapper

import com.mvaresedev.swapp.data.db.entities.CharacterEntity
import com.mvaresedev.swapp.data.db.entities.FilmEntity
import com.mvaresedev.swapp.data.db.entities.VehicleEntity
import com.mvaresedev.swapp.domain.mapper.DbMapper
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle

class DbMapperImpl : DbMapper {

    override fun mapCharacterEntityToDomain(characterEntity: CharacterEntity): Character {
        return with(characterEntity) {
            Character(id, birthYear, eyeColor, films, gender, hairColor, height, mass, name, skinColor, vehicles, avatarUrl)
        }
    }

    override fun mapCharacterDomainToEntities(characterList: List<Character>): List<CharacterEntity> {
        return characterList.map { c ->
            with(c){
                CharacterEntity(
                    id = id,
                    name = name,
                    birthYear = birthYear,
                    eyeColor = eyeColor,
                    height = height,
                    films = films,
                    gender = gender,
                    hairColor = hairColor,
                    mass = mass,
                    skinColor = skinColor,
                    vehicles = vehicles,
                    avatarUrl = avatarUrl
                )
            }

        }
    }

    override fun mapFilmEntityToDomain(filmEntity: FilmEntity): Film {
        return with(filmEntity) {
            Film(id, title, year.toString(), openingCrawl)
        }
    }

    override fun mapFilmDomainToEntity(films: List<Film>): List<FilmEntity> {
        return films.map { f ->
            with(f) {
                FilmEntity(
                    id = id,
                    title = title,
                    year = year.toInt(),
                    openingCrawl = openingCrawl
                )
            }
        }
    }

    override fun mapVehicleDomainToEntity(vehicles: List<Vehicle>): List<VehicleEntity> {
        return vehicles.map { v ->
            with(v) {
                VehicleEntity(id, name, model, vehicleClass, manufacturer, length, cost, maxSpeed)
            }
        }
    }

    override fun mapVehicleEntityToDomain(vehicleEntity: VehicleEntity): Vehicle {
        return with(vehicleEntity) {
            Vehicle(id, name, model, vehicleClass, manufacturer, length, cost, maxSpeed)
        }
    }

}