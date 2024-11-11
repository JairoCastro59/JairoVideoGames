package com.jdcastro.jairovideogames.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jdcastro.jairovideogames.data.database.dao.VideogameDAO
import com.jdcastro.jairovideogames.data.database.entities.VideogameEntity

@Database(entities = [VideogameEntity::class], version = 1)
abstract class VideogameDatabase: RoomDatabase() {

    abstract fun getVideogameDao(): VideogameDAO

}