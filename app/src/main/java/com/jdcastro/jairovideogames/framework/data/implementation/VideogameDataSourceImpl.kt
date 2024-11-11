package com.jdcastro.jairovideogames.framework.data.implementation

import com.jdcastro.jairovideogames.dashboard.framework.data.implementations.VideogamesMapper
import com.jdcastro.jairovideogames.framework.data.config.VideogameApiService
import javax.inject.Inject
import com.jdcastro.jairovideogames.data.dataSource.VideogameDataSource
import com.jdcastro.jairovideogames.domain.models.VideogameResult

class VideogameDataSourceImpl @Inject constructor(
    private val dasboardApiService: VideogameApiService,
    private val videogameMapper: VideogamesMapper
): VideogameDataSource {

    override suspend fun getVideogames(): Result<VideogameResult> {
        return try {
            val result = dasboardApiService.getVideogames().await()
            result.let {
                Result.success(videogameMapper.map(it))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}