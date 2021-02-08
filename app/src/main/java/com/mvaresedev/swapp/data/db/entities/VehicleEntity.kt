package com.mvaresedev.swapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val model: String,
    val vehicleClass: String,
    val manufacturer: String,
    val length: String,
    val cost: String,
    val maxSpeed: String
)