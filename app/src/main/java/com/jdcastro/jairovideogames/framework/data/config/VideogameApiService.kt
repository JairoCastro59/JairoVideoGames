package com.jdcastro.jairovideogames.framework.data.config

import com.jdcastro.jairovideogames.framework.data.config.response.Videogame
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface VideogameApiService {

    @GET("games")
    fun getVideogames(): Deferred<ArrayList<Videogame>>
}