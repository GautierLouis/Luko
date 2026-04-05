package com.louisgautier.dictionary.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.louisgautier.designsystem.components.page.LoadingContent
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.model.SimpleDictionary

/**
 * Preview on parent to handle all states of the screen
 */
@Composable
internal fun DictionaryContent(
    items: LazyPagingItems<SimpleDictionary>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    val state = rememberLazyGridState()

    val appendLoading = items.loadState.append is LoadState.Loading

    LazyVerticalGrid(
        columns = GridCells.Adaptive(CharacterItemDefault.MINIMUM_WIDTH.dp),
        state = state,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Padding.large),
        verticalArrangement = Spacing.large,
        horizontalArrangement = Spacing.large,
    ) {
        items(
            count = items.itemCount,
            key = { index -> items.peek(index)?.code ?: index }
        ) { index ->
            val dictionary = items[index]!!
            CharacterItem(
                dictionary = dictionary,
                onClick = { onItemClick(dictionary.code) }
            )
        }

        if (appendLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadingContent()
            }
        }
    }
}