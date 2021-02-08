package com.mvaresedev.swapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
class FilmEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val year: Int,
    val openingCrawl: String
)