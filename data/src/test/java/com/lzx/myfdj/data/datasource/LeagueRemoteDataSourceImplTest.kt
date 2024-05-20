package com.lzx.myfdj.data.datasource

import com.lzx.myfdj.data.api.TheSportsDbApi
import com.lzx.myfdj.data.api.model.LeagueDto
import com.lzx.myfdj.data.api.model.LeaguesDataDto
import com.lzx.myfdj.data.datasource.league.LeagueRemoteDataSourceImpl
import com.lzx.myfdj.data.exception.MyFdjException
import com.lzx.myfdj.data.mapper.LeagueMapper
import com.lzx.myfdj.domain.model.League
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

class LeagueRemoteDataSourceImplTest {

    private lateinit var leagueMapper: LeagueMapper
    private lateinit var theSportsDbApi: TheSportsDbApi
    private lateinit var tested: LeagueRemoteDataSourceImpl

    @Before
    fun setUp() {
        theSportsDbApi = mockk<TheSportsDbApi>()
        leagueMapper = mockk<LeagueMapper>()
        tested = LeagueRemoteDataSourceImpl(theSportsDbApi, leagueMapper, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getAllLeagues with success`() = runTest {
        // given
        val mockResponse = mockk<Response<LeaguesDataDto>>()
        coEvery { theSportsDbApi.getAllLeagues() } returns mockResponse
        val mockLeaguesDataDto = LeaguesDataDto(
            leagues = listOf(
                LeagueDto("1", name = "League 1"),
                LeagueDto("2", name = "League 2"),
            ),
        )
        coEvery { mockResponse.body() } returns mockLeaguesDataDto
        coEvery { mockResponse.isSuccessful } returns true
        val mockMappedLeague = mockk<League>()
        coEvery { leagueMapper.map(any()) } returns mockMappedLeague

        // when
        val result = tested.getAllLeagues()

        // then
        assertEquals(listOf(mockMappedLeague, mockMappedLeague), result)
    }

    @Test
    fun `test getAllLeagues with an error body`() = runTest {
        // given
        val mockResponse = mockk<Response<LeaguesDataDto>>()
        coEvery { theSportsDbApi.getAllLeagues() } returns mockResponse
        val mockLeaguesDataDto = LeaguesDataDto(
            leagues = listOf(
                LeagueDto("1", name = "League 1"),
                LeagueDto("1", name = "League 2"),
            ),
        )
        coEvery { mockResponse.body() } returns mockLeaguesDataDto
        coEvery { mockResponse.isSuccessful } returns false
        coEvery { mockResponse.code() } returns 403
        coEvery { mockResponse.errorBody() } returns null
        val mockMappedLeague = mockk<League>()
        coEvery { leagueMapper.map(any()) } returns mockMappedLeague

        // when
        val result = assertFails {
            tested.getAllLeagues()
        }

        // then
        assert(result is MyFdjException)
    }
}
