package com.jdcastro.jairovideogames.framework.di

import android.content.Context
import androidx.room.Room
import com.jdcastro.jairovideogames.data.database.VideogameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    const val VIDEOGAME_DATABASE = "videogame"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, VideogameDatabase::class.java, VIDEOGAME_DATABASE).build()

    @Singleton
    @Provides
    fun provideVideogameDap(db: VideogameDatabase) = db.getVideogameDao()
}