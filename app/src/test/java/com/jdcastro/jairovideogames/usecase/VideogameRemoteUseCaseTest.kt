package com.jdcastro.jairovideogames.usecase

import com.jdcastro.jairovideogames.data.repository.VideogameRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class VideogameRemoteUseCaseTest {

    @RelaxedMockK
    private lateinit var dashboardRepository: VideogameRepository

    lateinit var videogameLocalUseCase: VideogameLocalUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        videogameLocalUseCase = VideogameLocalUseCase(dashboardRepository)
    }

    @Test
    fun `cuando el api retorna vacio`() = runBlocking {
        //Given
        coEvery { dashboardRepository.getVideogamesFromDatabase() } returns arrayListOf()

        //When
        videogameLocalUseCase

        //Then
        coVerify(exactly = 1) {  dashboardRepository.getVideogamesFromApi() }
    }
}