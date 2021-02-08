package com.mvaresedev.swapp.domain.mapper

import com.mvaresedev.swapp.data.db.entities.CharacterEntity
import com.mvaresedev.swapp.data.db.entities.FilmEntity
import com.mvaresedev.swapp.data.db.entities.VehicleEntity
import com.mvaresedev.swapp.domain.models.Character
import com.mvaresedev.swapp.domain.models.Film
import com.mvaresedev.swapp.domain.models.Vehicle

interface DbMapper {
    fun mapCharacterEntityToDomain(characterEntity: CharacterEntity): Character
    fun mapCharacterDomainToEntities(characterList: List<Character>): List<CharacterEntity>
    fun mapFilmEntityToDomain(filmEntity: FilmEntity): Film
    fun mapFilmDomainToEntity(films: List<Film>): List<FilmEntity>
    fun mapVehicleDomainToEntity(vehicles: List<Vehicle>): List<VehicleEntity>
    fun mapVehicleEntityToDomain(vehicleEntity: VehicleEntity): Vehicle
}