package com.lzx.myfdj.data.repository

import com.lzx.myfdj.data.datasource.team.TeamRemoteDataSource
import com.lzx.myfdj.domain.model.Team
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class TeamRepositoryImplTest {

    private lateinit var tested: TeamRepositoryImpl
    private lateinit var teamRemoteDataSource: TeamRemoteDataSource

    @Before
    fun setUp() {
        teamRemoteDataSource = mockk()
        tested = TeamRepositoryImpl(teamRemoteDataSource, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getAllLeagues repository`() = runTest {
        // given
        val mockTeams = listOf(
            Team(id = "1", name = "Team 1", badgeUrl = "badge 1"),
            Team(id = "2", name = "Team 2", badgeUrl = "badge 2"),
        )
        coEvery { teamRemoteDataSource.getTeamsByLeague("query") } returns mockTeams

        // when (display every other item)
        val result = tested.getTeamsByLeague("query").first()

        // then
        assert(result.size == 1)
        assert(result == mockTeams.filterIndexed { index, _ -> index % 2 == 0 })
    }
}
