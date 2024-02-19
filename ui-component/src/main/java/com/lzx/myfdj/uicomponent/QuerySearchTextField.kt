package com.lzx.myfdj.uicomponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun QuerySearchTextField(
    modifier: Modifier = Modifier,
    query: String,
    onClearClick: () -> Unit,
    onQueryChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
            )
        },
        value = query,
        onValueChange = {
            onQueryChanged(it)
        },
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        },
        trailingIcon = {
            IconButton(onClick = {
                onClearClick()
            }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "Clear")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
    )
}
