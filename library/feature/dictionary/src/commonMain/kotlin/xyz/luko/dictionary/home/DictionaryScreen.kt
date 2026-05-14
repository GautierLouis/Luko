package xyz.luko.dictionary.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.luko.baseui.PagingDataPreviewParameter
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.components.page.ErrorContent
import xyz.luko.designsystem.components.page.LoadingContent
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.dictionary.details.ModalCharacterDetails
import xyz.luko.dictionary.details.ModalCharacterDetailsViewModel
import xyz.luko.dictionary.home.DictionaryScreenEvent.OnCharacterClicked
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.previewSimpleDataList

@Composable
fun DictionaryScreen() {
    val viewModel = koinViewModel<DictionaryListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val items = viewModel.items.collectAsLazyPagingItems()

    DictionaryScreen(
        state = state,
        items = items,
        onEvent = { event -> viewModel.onEventReceived(event) },
    )
}

@Composable
private fun DictionaryScreen(
    state: DictionaryListViewModel.UIState,
    items: LazyPagingItems<SimpleDictionary>,
    onEvent: (DictionaryScreenEvent) -> Unit = {},
) {
    val isError = items.loadState.refresh is LoadState.Error
    val isLoading = items.loadState.refresh is LoadState.Loading

    if (state.selectedCharacter != null) {
        val modalVm =
            koinViewModel<ModalCharacterDetailsViewModel>(
                parameters = { parametersOf(state.selectedCharacter) },
            )

        ModalCharacterDetails(
            viewModel = modalVm,
            onDismiss = {
                onEvent(DictionaryScreenEvent.OnModalDismiss)
            },
        )
    }

    val contentTopCorner =
        if (state.filterMenuExpended) {
            Padding.extraLarge
        } else {
            Padding.none
        }

    BaseScaffold(
        topBar = {
            DictionaryTopBar(
                textFieldState = state.textFieldState,
                filterMenuExpended = state.filterMenuExpended,
                activeFilter = state.activeFilter,
                enabled = !isError && !isLoading,
                onEvent = onEvent,
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .background(
                        color = Theme.materialColors.background,
                        shape =
                            RoundedCornerShape(
                                topStart = contentTopCorner,
                                topEnd = contentTopCorner,
                            ),
                    ),
        ) {
            when {
                isLoading -> {
                    LoadingContent(
                        modifier =
                            Modifier
                                .fillMaxHeight(),
                    )
                }

                isError -> {
                    ErrorContent(
                        modifier =
                            Modifier
                                .fillMaxHeight(),
                        action = { items.retry() },
                    )
                }

                else ->
                    DictionaryContent(
                        items = items,
                        onItemClick = {
                            onEvent(OnCharacterClicked(it))
                        },
                    )
            }
        }
    }
}

private class DictionaryScreenProvider :
    PagingDataPreviewParameter<SimpleDictionary>(previewSimpleDataList)

@Preview
@Composable
private fun PreviewDictionaryScreenDay(
    @PreviewParameter(DictionaryScreenProvider::class) pagingData: PagingData<SimpleDictionary>,
) {
    AppTheme(ThemeMode.Day) {
        DictionaryScreen(
            state =
                DictionaryListViewModel.UIState(
                    selectedCharacter = null,
                    filterMenuExpended = false,
                ),
            items = flowOf(pagingData).collectAsLazyPagingItems(),
        )
    }
}

@Preview
@Composable
private fun PreviewDictionaryScreenNight(
    @PreviewParameter(DictionaryScreenProvider::class) pagingData: PagingData<SimpleDictionary>,
) {
    AppTheme(ThemeMode.Night) {
        DictionaryScreen(
            state =
                DictionaryListViewModel.UIState(
                    selectedCharacter = null,
                    filterMenuExpended = false,
                ),
            items = flowOf(pagingData).collectAsLazyPagingItems(),
        )
    }
}
