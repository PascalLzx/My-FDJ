package com.lzx.myfdj.presentation.main.model

import com.lzx.myfdj.uicomponent.model.TeamUi

sealed interface TeamsByLeagueUiState {

    data object Init : TeamsByLeagueUiState
    data object Loading : TeamsByLeagueUiState
    data class Error(val throwable: Throwable) : TeamsByLeagueUiState
    data class Success(val data: List<TeamUi>) : TeamsByLeagueUiState
}
