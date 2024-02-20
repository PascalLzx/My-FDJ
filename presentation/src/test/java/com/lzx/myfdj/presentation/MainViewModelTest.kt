package com.lzx.myfdj.presentation

import com.lzx.myfdj.domain.model.League
import com.lzx.myfdj.domain.model.Team
import com.lzx.myfdj.domain.repository.LeagueRepository
import com.lzx.myfdj.domain.usecase.GetAllLeaguesUseCase
import com.lzx.myfdj.domain.usecase.GetTeamsByLeagueUseCase
import com.lzx.myfdj.presentation.main.MainViewModel
import com.lzx.myfdj.presentation.main.mapper.TeamUiMapper
import com.lzx.myfdj.presentation.main.model.AllLeaguesUiState
import com.lzx.myfdj.presentation.main.model.TeamsByLeagueUiState
import com.lzx.myfdj.presentation.util.MainDispatcherTestRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherTestRule()

    private lateinit var tested: MainViewModel
    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase
    private lateinit var teamUiMapper: TeamUiMapper
    private lateinit var fakeLeagueRepository: FakeLeagueRepository

    @Before
    fun setup() {
        fakeLeagueRepository = FakeLeagueRepository()
        getAllLeaguesUseCase = GetAllLeaguesUseCase(fakeLeagueRepository)
        getTeamsByLeagueUseCase = mockk()
        teamUiMapper = TeamUiMapper()

        tested = MainViewModel(
            getAllLeaguesUseCase,
            getTeamsByLeagueUseCase,
            teamUiMapper,
            Dispatchers.Unconfined,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test loading state AllLeaguesUiState`() = runTest {
        // given

        // then
        assertEquals(tested.allLeaguesUiState.first(), AllLeaguesUiState.Loading)
    }

    @Test
    fun `test success state AllLeaguesUiState`() = runTest {
        // Given
        val expectedLeagues = listOf(League(id = "1", name = "Team 1"))
        val collectJob = launch(UnconfinedTestDispatcher()) { tested.allLeaguesUiState.collect() }

        // When
        fakeLeagueRepository.emit(expectedLeagues)

        // Then
        val result = tested.allLeaguesUiState.value
        assertEquals(
            AllLeaguesUiState.Success(expectedLeagues.map { it.name }),
            result,
        )
        collectJob.cancel()
    }

    @Test
    fun `test init state TeamsByLeagueUiState`() = runTest {
        // given
        coEvery { getTeamsByLeagueUseCase("Premier League") } returns mockk()

        // then
        assertEquals(tested.teamsByLeagueUiState.first(), TeamsByLeagueUiState.Init)
    }

    @Test
    fun `test success state TeamsByLeagueUiState`() = runTest {
        // Given
        val leagueName = "Premier League"
        val expectedTeams = listOf(Team(id = "1", name = "Team 1", badgeUrl = ""))
        val resultFlow: Flow<List<Team>> = flowOf(expectedTeams)
        coEvery { getTeamsByLeagueUseCase(leagueName) } returns resultFlow

        // When
        tested.getTeamsByLeague(leagueName)

        // Then
        resultFlow.collect {
            assertEquals(
                TeamsByLeagueUiState.Success(expectedTeams.map { teamUiMapper.map(it) }),
                tested.teamsByLeagueUiState.value,
            )
        }
    }

    @Test
    fun `test update search query`() = runTest {
        // Given
        val leagueName = "Premier League"

        // When
        tested.updateSearchQuery(leagueName)

        // Then
        assertEquals(
            leagueName,
            tested.searchQuery.value,
        )
    }

    @Test
    fun `test clear search query and suggestion list`() = runTest {
        // Given
        val leagueName = "Premier League"

        // When
        tested.updateSearchQuery(leagueName)
        tested.clearSearchQuery()

        // Then
        assertEquals(
            "",
            tested.searchQuery.value,
        )
        assertEquals(
            emptyList<String>(),
            tested.suggestionList.value,
        )
    }

    inner class FakeLeagueRepository : LeagueRepository {

        private val flow = MutableSharedFlow<List<League>>()

        suspend fun emit(value: List<League>) = flow.emit(value)

        override fun getAllLeagues(): Flow<List<League>> = flow
    }
}
