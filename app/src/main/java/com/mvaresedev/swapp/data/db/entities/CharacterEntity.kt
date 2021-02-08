package com.mvaresedev.swapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val birthYear: String,
    val eyeColor: String?,
    val films: List<Int>,
    val gender: String?,
    val hairColor: String?,
    val height: String,
    val mass: String,
    val name: String,
    val skinColor: String,
    val vehicles: List<Int>,
    val avatarUrl: String
)