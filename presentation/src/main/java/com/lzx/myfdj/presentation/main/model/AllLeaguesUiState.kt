package com.lzx.myfdj.presentation.main.model

sealed interface AllLeaguesUiState {
    data object Loading : AllLeaguesUiState
    data class Error(val throwable: Throwable) : AllLeaguesUiState
    data class Success(val data: List<String>) : AllLeaguesUiState
}
