package com.lzx.myfdj.data.repository

import com.lzx.myfdj.data.datasource.league.LeagueRemoteDataSource
import com.lzx.myfdj.domain.model.League
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class LeagueRepositoryImplTest {

    private lateinit var tested: LeagueRepositoryImpl
    private lateinit var leagueRemoteDataSource: LeagueRemoteDataSource

    @Before
    fun setUp() {
        leagueRemoteDataSource = mockk()
        tested = LeagueRepositoryImpl(leagueRemoteDataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getAllLeagues repository`() = runTest {
        // given
        val mockLeagues = listOf(
            League(id = "1", name = "League 1"),
            League(id = "2", name = "League 2"),
        )
        coEvery { leagueRemoteDataSource.getAllLeagues() } returns mockLeagues

        // when
        val result = tested.getAllLeagues().first()

        // then
        assert(result == mockLeagues)
    }
}
