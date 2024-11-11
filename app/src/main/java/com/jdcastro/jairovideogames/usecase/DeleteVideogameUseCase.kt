package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.database.entities.toEntity
import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import javax.inject.Inject

class DeleteVideogameUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    suspend operator fun invoke(item: VideogameObj) {
        return dashboardRepository.deleteVideogame(item.toEntity())
    }
}