package com.lzx.myfdj.domain.usecase

import com.lzx.myfdj.domain.model.Team
import com.lzx.myfdj.domain.repository.TeamRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetTeamsByLeagueUseCaseTest {

    @Test
    fun `test GetTeamsByLeagueUseCase`() = runTest {
        // Given
        val teamRepository = mockk<TeamRepository>()
        val tested = GetTeamsByLeagueUseCase(teamRepository)
        val leagueName = "Premier League"
        val expectedTeams = listOf(Team(id = "1", name = "Team 1", badgeUrl = ""))
        val resultFlow = flowOf(expectedTeams)
        coEvery { teamRepository.getTeamsByLeague(leagueName) } returns resultFlow

        // When
        val result = tested(leagueName).first()

        // Then
        assertEquals(expectedTeams, result)
    }
}
