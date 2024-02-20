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
    searchQuery: String,
    suggestionList: List<String>,
    onItemClick: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClearSearchQueryClick: () -> Unit,
    suggestionItem: @Composable (String) -> Unit,
) {
    var isSuggestionListVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        SearchQueryTextField(
            modifier = Modifier.height(Dimensions.queryTextFieldHeight),
            onSearchQueryChanged = {
                onSearchQueryChange(it)
                isSuggestionListVisible = it.isNotEmpty()
            },
            onClearSearchQueryClick = {
                onClearSearchQueryClick()
            },
            searchQuery = searchQuery,
        )

        if (isSuggestionListVisible && suggestionList.isNotEmpty()) {
            SuggestionList(
                suggestionList = suggestionList,
                onItemClick = { suggestion ->
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
    suggestionList: List<String>,
    onItemClick: (String) -> Unit,
    suggestionItem: @Composable (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(Dimensions.suggestionListPadding),
        modifier = modifier,
    ) {
        items(suggestionList) { suggestion ->
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
            searchQuery = "",
            onSearchQueryChange = {},
            suggestionList = listOf("PSG, Paris, Paris-Saint-Germain"),
            onItemClick = {},
            onClearSearchQueryClick = {},
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
            suggestionList = listOf("PSG, Paris, Paris-Saint-Germain"),
            onItemClick = {},
        ) {
            Text(text = it)
        }
    }
}
