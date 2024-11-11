package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.database.entities.toEntity
import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import javax.inject.Inject

class VideogameRemoteUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    suspend operator fun invoke(): ArrayList<VideogameObj> {
        val response = dashboardRepository.getVideogamesFromApi()
        dashboardRepository.insertVideogames(response.map { it.toEntity() })
        return response
    }
}