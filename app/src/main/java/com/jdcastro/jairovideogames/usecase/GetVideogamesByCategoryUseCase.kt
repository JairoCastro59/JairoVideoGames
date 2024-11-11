package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import javax.inject.Inject

class GetVideogamesByCategoryUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    suspend operator fun invoke(category: String): ArrayList<VideogameObj> {
        return dashboardRepository.getVideogamesByCategory(category)
    }
}