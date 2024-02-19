package com.lzx.myfdj.data.datasource

import com.lzx.myfdj.data.api.TheSportsDbApi
import com.lzx.myfdj.data.api.model.TeamDto
import com.lzx.myfdj.data.api.model.TeamsDataDto
import com.lzx.myfdj.data.datasource.team.TeamRemoteDataSourceImpl
import com.lzx.myfdj.data.exception.ApiException
import com.lzx.myfdj.data.mapper.TeamMapper
import com.lzx.myfdj.domain.model.Team
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertFails

class TeamRemoteDataSourceImplTest {

    private lateinit var teamMapper: TeamMapper
    private lateinit var theSportsDbApi: TheSportsDbApi
    private lateinit var tested: TeamRemoteDataSourceImpl

    @Before
    fun setUp() {
        theSportsDbApi = mockk<TheSportsDbApi>()
        teamMapper = mockk<TeamMapper>()
        tested = TeamRemoteDataSourceImpl(theSportsDbApi, teamMapper, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getTeamsByLeague with success`() = runTest {
        // given
        val mockResponse = mockk<Response<TeamsDataDto>>()
        coEvery { theSportsDbApi.getTeamsByLeague(any()) } returns mockResponse
        val mockTeamsDataDto = TeamsDataDto(
            teams = listOf(
                TeamDto("1", name = "Team 1", badgeUrl = ""),
            ),
        )
        coEvery { mockResponse.body() } returns mockTeamsDataDto
        coEvery { mockResponse.isSuccessful } returns true
        val mockMappedTeam = mockk<Team>()
        coEvery { teamMapper.map(any()) } returns mockMappedTeam

        // when
        val result = tested.getTeamsByLeague("Premier League")

        // then
        assertEquals(listOf(mockMappedTeam), result)
    }

    @Test
    fun `test getTeamsByLeague with an error body`() = runTest {
        // given
        val mockResponse = mockk<Response<TeamsDataDto>>()
        coEvery { theSportsDbApi.getTeamsByLeague(any()) } returns mockResponse
        val mockTeamsDataDto = TeamsDataDto(
            teams = listOf(
                TeamDto("1", name = "Team 1", badgeUrl = ""),
            ),
        )
        coEvery { mockResponse.body() } returns mockTeamsDataDto
        coEvery { mockResponse.isSuccessful } returns false
        coEvery { mockResponse.code() } returns 403
        coEvery { mockResponse.errorBody() } returns null
        val mockMappedTeam = mockk<Team>()
        coEvery { teamMapper.map(any()) } returns mockMappedTeam

        // when
        val result = assertFails {
            tested.getTeamsByLeague("Premier League")
        }

        // then
        assert(result is ApiException)
    }
}
