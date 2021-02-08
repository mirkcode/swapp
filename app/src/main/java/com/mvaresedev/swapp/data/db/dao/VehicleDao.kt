package com.mvaresedev.swapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.swapp.data.db.entities.VehicleEntity

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVehicles(vehicleList: List<VehicleEntity>)

    @Query("SELECT * FROM vehicles WHERE id = :id")
    suspend fun getVehicleById(id: Int): VehicleEntity?
}