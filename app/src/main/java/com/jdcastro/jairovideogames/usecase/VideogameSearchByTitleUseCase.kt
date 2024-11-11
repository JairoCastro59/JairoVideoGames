package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import javax.inject.Inject

class VideogameSearchByTitleUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    suspend operator fun invoke(title: String): ArrayList<VideogameObj> {
        return dashboardRepository.getVideogamesSearchByTitle(title)
    }
}