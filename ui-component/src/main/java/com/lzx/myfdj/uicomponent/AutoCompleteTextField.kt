package com.lzx.myfdj.uicomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lzx.myfdj.uicomponent.theme.Dimensions
import com.lzx.myfdj.uicomponent.theme.MyFDJTheme

@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    suggestionList: List<String>,
    onItemClick: (String) -> Unit,
    suggestionItem: @Composable (String) -> Unit,
) {
    var filteredSuggestions by rememberSaveable { mutableStateOf(emptyList<String>()) }
    var query by rememberSaveable { mutableStateOf("") }
    var isSuggestionListVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        QuerySearchTextField(
            modifier = Modifier.height(Dimensions.queryTextFieldHeight),
            onQueryChanged = { queryChanged ->
                query = queryChanged
                filteredSuggestions = suggestionList.filter { suggestion ->
                    suggestion.contains(
                        other = queryChanged.trim(),
                        ignoreCase = true,
                    )
                }
                isSuggestionListVisible = queryChanged.isNotEmpty()
            },
            onClearClick = {
                query = ""
                filteredSuggestions = emptyList()
            },
            query = query,
        )

        if (isSuggestionListVisible && filteredSuggestions.isNotEmpty()) {
            SuggestionList(
                filteredSuggestions = filteredSuggestions,
                onItemClick = { suggestion ->
                    query = suggestion
                    onItemClick(suggestion)
                    isSuggestionListVisible = false
                },
                suggestionItem = suggestionItem,
            )
        }
    }
}

@Composable
private fun SuggestionList(
    modifier: Modifier = Modifier,
    filteredSuggestions: List<String>,
    onItemClick: (String) -> Unit,
    suggestionItem: @Composable (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(Dimensions.suggestionListPadding),
        modifier = modifier,
    ) {
        items(filteredSuggestions) { suggestion ->
            Box(
                Modifier
                    .padding(Dimensions.itemPadding)
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(suggestion)
                    },
            ) {
                suggestionItem(suggestion)
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
private fun AutoCompleteTextFieldPreview() {
    MyFDJTheme {
        AutoCompleteTextField(
            suggestionList = listOf("PSG, Paris, Paris-Saint-Germain"),
            onItemClick = {},
        ) {
            Text(text = it)
        }
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
private fun SuggestionListPreview() {
    MyFDJTheme {
        SuggestionList(
            filteredSuggestions = listOf("PSG, Paris, Paris-Saint-Germain"),
            onItemClick = {},
        ) {
            Text(text = it)
        }
    }
}
