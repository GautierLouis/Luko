package xyz.luko.dictionary.home

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
import xyz.luko.designsystem.components.page.LoadingContent
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.SimpleDictionary

/**
 * Preview on parent to handle all states of the screen
 */
@Composable
internal fun DictionaryContent(
    items: LazyPagingItems<SimpleDictionary>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
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
            key = { index -> items.peek(index)?.code ?: index },
        ) { index ->
            val dictionary = items[index]!!
            CharacterItem(
                dictionary = dictionary,
                onClick = { onItemClick(dictionary.code) },
            )
        }

        if (appendLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadingContent()
            }
        }
    }
}
