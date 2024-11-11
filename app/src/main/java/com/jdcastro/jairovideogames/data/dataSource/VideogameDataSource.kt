package com.jdcastro.jairovideogames.data.dataSource

import com.jdcastro.jairovideogames.domain.models.VideogameResult

interface VideogameDataSource {
    suspend fun getVideogames(): Result<VideogameResult>
}