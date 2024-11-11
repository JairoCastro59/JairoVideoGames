package com.jdcastro.jairovideogames.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdcastro.jairovideogames.data.database.entities.VideogameEntity

@Dao
interface VideogameDAO {

    @Query("SELECT * FROM videogame ORDER BY id DESC")
    suspend fun getAllVideogames(): List<VideogameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videogames: List<VideogameEntity>)

    @Query("SELECT * FROM videogame WHERE title LIKE:title")
    suspend fun getVideogamesSearchByTitle(title: String): List<VideogameEntity>

    @Query("SELECT * FROM videogame WHERE id =:idDetail ")
    suspend fun getVideogameDetail(idDetail: Int): VideogameEntity

    @Delete
    suspend fun deleteVideogame(item: VideogameEntity)

    @Query("SELECT DISTINCT genre FROM videogame")
    suspend fun getCategories(): List<String>

    @Query("SELECT * FROM videogame WHERE genre =:category")
    suspend fun getVideogamesByCategory(category: String): List<VideogameEntity>

}