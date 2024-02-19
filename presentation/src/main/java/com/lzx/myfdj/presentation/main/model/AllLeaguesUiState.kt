package com.lzx.myfdj.presentation.main.model

import com.lzx.myfdj.uicomponent.model.LeagueUi

sealed interface AllLeaguesUiState {
    data object Loading : AllLeaguesUiState
    data class Error(val throwable: Throwable) : AllLeaguesUiState
    data class Success(val data: List<LeagueUi>) : AllLeaguesUiState
}
