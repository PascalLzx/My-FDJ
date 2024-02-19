package com.lzx.myfdj.uicomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.lzx.myfdj.uicomponent.model.TeamUi
import com.lzx.myfdj.uicomponent.theme.Dimensions
import com.lzx.myfdj.uicomponent.theme.MyFDJTheme

@Composable
fun TeamList(data: List<TeamUi>) {
    val lazyGridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = Modifier.padding(top = Dimensions.queryTextFieldHeight),
        state = lazyGridState,
        columns = GridCells.Fixed(GRID_CELLS),
        verticalArrangement = Arrangement.spacedBy(Dimensions.teamListPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.teamListPadding),
    ) {
        items(data) { team ->
            Column(
                Modifier
                    .padding(Dimensions.itemPadding)
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    model = team.badgeUrl,
                    contentDescription = team.name,
                )
            }
        }
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
private fun TeamListPreview() {
    MyFDJTheme {
        TeamList(
            data = listOf(
                TeamUi(
                    id = "0",
                    name = "Brest",
                    badgeUrl = "https://www.thesportsdb.com/images/media/team/badge/z69be41598797026.png",
                ),
            ),
        )
    }
}

private const val GRID_CELLS = 2
