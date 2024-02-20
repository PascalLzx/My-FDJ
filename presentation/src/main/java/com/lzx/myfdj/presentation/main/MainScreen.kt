package com.lzx.myfdj.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lzx.myfdj.presentation.main.model.AllLeaguesUiState
import com.lzx.myfdj.presentation.main.model.TeamsByLeagueUiState
import com.lzx.myfdj.uicomponent.AutoCompleteTextField
import com.lzx.myfdj.uicomponent.TeamList
import com.lzx.myfdj.uicomponent.theme.Dimensions
import timber.log.Timber

@Composable
fun MainActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    Box(modifier.fillMaxSize()) {
        val snackBarHostState = remember { SnackbarHostState() }
        val allLeaguesUiState by viewModel.allLeaguesUiState.collectAsStateWithLifecycle()
        val teamsByLeagueUiState by viewModel.teamsByLeagueUiState.collectAsStateWithLifecycle()
        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
        val suggestionList by viewModel.suggestionList.collectAsStateWithLifecycle()

        if (teamsByLeagueUiState is TeamsByLeagueUiState.Loading || allLeaguesUiState is AllLeaguesUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).width(Dimensions.loaderSize))
        }
        ManageTeamsByLeagueUiState(
            teamsByLeagueUiState = teamsByLeagueUiState,
            onError = { Timber.e(it) },
        )
        ManageAllLeaguesUiState(
            allLeaguesUiState = allLeaguesUiState,
            searchQuery = searchQuery,
            suggestionList = suggestionList,
            onError = { ShowSnackBar(snackBarHostState, it ?: "") },
            onItemClick = { viewModel.getTeamsByLeague(leagueName = it) },
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            onClearSearchQueryClick = { viewModel.clearSearchQuery() },
        )
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun ManageTeamsByLeagueUiState(
    teamsByLeagueUiState: TeamsByLeagueUiState,
    onError: (String?) -> Unit,
) {
    when (teamsByLeagueUiState) {
        is TeamsByLeagueUiState.Success -> {
            TeamList(teamsByLeagueUiState.data)
        }

        is TeamsByLeagueUiState.Error -> {
            onError(teamsByLeagueUiState.throwable.message)
        }

        TeamsByLeagueUiState.Loading,
        TeamsByLeagueUiState.Init,
        -> {
        }
    }
}

@Composable
private fun ManageAllLeaguesUiState(
    allLeaguesUiState: AllLeaguesUiState,
    searchQuery: String,
    suggestionList: List<String>,
    onSearchQueryChange: (String) -> Unit,
    onClearSearchQueryClick: () -> Unit,
    onError: @Composable (String?) -> Unit,
    onItemClick: (String) -> Unit,
) {
    when (allLeaguesUiState) {
        is AllLeaguesUiState.Success -> {
            AutoCompleteTextField(
                searchQuery = searchQuery,
                suggestionList = suggestionList,//allLeaguesUiState.data,
                onItemClick = { onItemClick(it) },
                suggestionItem = { Text(text = it) },
                onSearchQueryChange = { onSearchQueryChange(it) },
                onClearSearchQueryClick = { onClearSearchQueryClick() },
            )
        }

        is AllLeaguesUiState.Error -> {
            onError(allLeaguesUiState.throwable.message)
        }

        AllLeaguesUiState.Loading -> {}
    }
}

@Composable
private fun ShowSnackBar(
    snackBarHostState: SnackbarHostState,
    message: String,
) {
    Timber.e(message)
    LaunchedEffect(snackBarHostState) {
        snackBarHostState.showSnackbar(
            message = message,
            actionLabel = "Dismiss",
        )
    }
}
