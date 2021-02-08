package com.mvaresedev.swapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.swapp.data.db.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characterList: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("SELECT COUNT(id) FROM characters")
    suspend fun getCharactersCount(): Int

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("DELETE FROM characters")
    suspend fun clearAllCharacters()
}