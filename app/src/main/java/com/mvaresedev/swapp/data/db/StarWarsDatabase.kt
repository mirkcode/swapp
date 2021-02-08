package com.mvaresedev.swapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvaresedev.swapp.data.db.dao.CharacterDao
import com.mvaresedev.swapp.data.db.dao.FilmDao
import com.mvaresedev.swapp.data.db.dao.RemoteKeyDao
import com.mvaresedev.swapp.data.db.dao.VehicleDao
import com.mvaresedev.swapp.data.db.entities.CharacterEntity
import com.mvaresedev.swapp.data.db.entities.FilmEntity
import com.mvaresedev.swapp.data.db.entities.RemoteKeyEntity
import com.mvaresedev.swapp.data.db.entities.VehicleEntity


@Database(entities = [CharacterEntity::class, RemoteKeyEntity::class, FilmEntity::class, VehicleEntity::class], version = 1)
@TypeConverters(DbTypeConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
    abstract fun getRemoteKeyDao(): RemoteKeyDao
    abstract fun getFilmDao(): FilmDao
    abstract fun getVehicleDao(): VehicleDao

    companion object {
        private const val DB_NAME = "starwars_db"

        fun create(context: Context): StarWarsDatabase {
            return Room.databaseBuilder(
                context,
                StarWarsDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
