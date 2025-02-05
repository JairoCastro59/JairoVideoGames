package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.database.entities.toEntity
import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideogameRemoteUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    val videogameList: Flow<ArrayList<VideogameObj>>  = flow {
        val response = dashboardRepository.getVideogamesFromApi()
        if (response.isNotEmpty()) {
            dashboardRepository.insertVideogames(response.map { it.toEntity() })
        }
        emit(response)
    }
}