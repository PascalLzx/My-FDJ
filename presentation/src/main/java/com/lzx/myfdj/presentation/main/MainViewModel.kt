package com.lzx.myfdj.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lzx.myfdj.common.di.DefaultDispatcher
import com.lzx.myfdj.domain.usecase.GetAllLeaguesUseCase
import com.lzx.myfdj.domain.usecase.GetTeamsByLeagueUseCase
import com.lzx.myfdj.presentation.Result
import com.lzx.myfdj.presentation.asResult
import com.lzx.myfdj.presentation.main.mapper.LeagueUiMapper
import com.lzx.myfdj.presentation.main.mapper.TeamUiMapper
import com.lzx.myfdj.presentation.main.model.AllLeaguesUiState
import com.lzx.myfdj.presentation.main.model.TeamsByLeagueUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAllLeaguesUseCase: GetAllLeaguesUseCase,
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase,
    private val leagueUiMapper: LeagueUiMapper,
    private val teamUiMapper: TeamUiMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val allLeaguesUiState: StateFlow<AllLeaguesUiState> = getAllLeaguesUseCase()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> AllLeaguesUiState.Success(
                    data = result.data.map { leagueUiMapper.map(league = it) },
                )

                Result.Loading -> AllLeaguesUiState.Loading

                is Result.Error -> AllLeaguesUiState.Error(result.exception)
            }
        }
        .flowOn(defaultDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AllLeaguesUiState.Loading,
        )

    private val _teamsByLeagueUiState: MutableStateFlow<TeamsByLeagueUiState> = MutableStateFlow(TeamsByLeagueUiState.Init)
    val teamsByLeagueUiState: StateFlow<TeamsByLeagueUiState> = _teamsByLeagueUiState

    fun getTeamsByLeague(leagueName: String) {
        viewModelScope.launch {
            getTeamsByLeagueUseCase(leagueName)
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _teamsByLeagueUiState.value = withContext(defaultDispatcher) {
                                TeamsByLeagueUiState.Success(
                                    data = result.data.map { teamUiMapper.map(team = it) },
                                )
                            }
                        }

                        Result.Loading -> TeamsByLeagueUiState.Loading

                        is Result.Error -> TeamsByLeagueUiState.Error(result.exception)
                    }
                }
        }
    }
}
