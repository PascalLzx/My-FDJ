package com.lzx.myfdj.domain.usecase

import com.lzx.myfdj.domain.model.League
import com.lzx.myfdj.domain.repository.LeagueRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAllLeaguesUseCaseTest {

    @Test
    fun `test GetAllLeaguesUseCase`() = runTest {
        // Given
        val leagueRepository = mockk<LeagueRepository>()
        val tested = GetAllLeaguesUseCase(leagueRepository)
        val expectedLeagues = listOf(League(id = "1", name = "League 1"))
        val resultFlow = flowOf(expectedLeagues)
        every { leagueRepository.getAllLeagues() } returns resultFlow

        // When
        val result = tested().first()

        // Then
        assertEquals(expectedLeagues, result)
    }
}
