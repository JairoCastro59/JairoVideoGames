package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import javax.inject.Inject

class GetVideogameDetailUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    suspend operator fun invoke(id: Int): VideogameObj {
        return dashboardRepository.getVideogameDetail(id)
    }
}