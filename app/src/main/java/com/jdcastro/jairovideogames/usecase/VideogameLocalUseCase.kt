package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import com.jdcastro.jairovideogames.domain.models.VideogameResult
import javax.inject.Inject

class VideogameLocalUseCase  @Inject constructor(
    private val dashboardRepository: VideogameRepository
) {
    suspend operator fun invoke(): ArrayList<VideogameObj> {
        return dashboardRepository.getVideogamesFromDatabase()
    }
}