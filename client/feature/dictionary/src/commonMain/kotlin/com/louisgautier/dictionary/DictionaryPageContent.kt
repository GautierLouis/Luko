package com.louisgautier.dictionary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.louisgautier.domain.model.SimpleDictionary

@Composable
fun DictionaryPageContent(
    items: LazyPagingItems<SimpleDictionary>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp,),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = items.itemCount,
            key = { index -> items.peek(index)?.code ?: index }
        ) { index ->
            val dictionary = items[index]!!
            CharacterRow(dictionary = dictionary, onClick = { onItemClick(dictionary.code) })
        }

        // Footer: show loading / error when appending pages
        if (items.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (items.loadState.append is LoadState.Error) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Failed to load more")
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { items.retry() }) { Text("Retry") }
                }
            }
        }
    }
}