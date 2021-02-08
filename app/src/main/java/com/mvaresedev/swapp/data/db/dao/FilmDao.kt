package com.mvaresedev.swapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.swapp.data.db.entities.FilmEntity

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFilms(filmList: List<FilmEntity>)

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getFilmById(id: Int): FilmEntity?

}