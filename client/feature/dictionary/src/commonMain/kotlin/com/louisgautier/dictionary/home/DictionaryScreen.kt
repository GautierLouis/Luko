package com.louisgautier.dictionary.home

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.louisgautier.designsystem.components.page.ErrorContent
import com.louisgautier.designsystem.components.page.LoadingContent
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.dictionary.PagingDataPreviewParameter
import com.louisgautier.dictionary.details.ModalCharacterDetails
import com.louisgautier.dictionary.details.ModalCharacterDetailsViewModel
import com.louisgautier.dictionary.home.DictionaryScreenEvent.OnCharacterClicked
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.previewSimpleDataList
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryScreen() {
    val viewModel = koinViewModel<DictionaryListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    DictionaryScreen(
        state = state,
        onEvent = { event -> viewModel.onEventReceived(event) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DictionaryScreen(
    state: DictionaryListViewModel.UIState,
    onEvent: (DictionaryScreenEvent) -> Unit = {},
) {

    val items = state.dictionaries.collectAsLazyPagingItems()

    //Global Error
    val isError = items.loadState.refresh is LoadState.Error
    val isLoading = items.loadState.refresh is LoadState.Loading

    if (state.selectedCharacter != null) {
        val modalVm = koinViewModel<ModalCharacterDetailsViewModel>(
            parameters = { parametersOf(state.selectedCharacter) }
        )

        ModalCharacterDetails(
            viewModel = modalVm,
            onDismiss = {
                onEvent(DictionaryScreenEvent.OnModalDismiss)
            }
        )
    }

    val contentTopCorner = if (state.filterMenuExpended) Padding.extraLarge
    else Padding.none

    Scaffold(
        topBar = {
            DictionaryTopBar(
                textFieldState = state.textFieldState,
                filterMenuExpended = state.filterMenuExpended,
                activeFilter = state.activeFilter,
                enabled = !isError && !isLoading,
                onEvent = onEvent
            )
        },
        containerColor = Theme.materialColors.surfaceContainer
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .background(
                    color = Theme.materialColors.background,
                    shape = RoundedCornerShape(
                        topStart = contentTopCorner,
                        topEnd = contentTopCorner
                    )
                )
        ) {
            when {
                isLoading -> {
                    LoadingContent(
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                }

                isError -> {
                    ErrorContent(
                        modifier = Modifier
                            .fillMaxHeight(),
                        action = { items.retry() }
                    )
                }

                else -> DictionaryContent(
                    items = items,
                    onItemClick = {
                        onEvent(OnCharacterClicked(it))
                    }
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
    @PreviewParameter(DictionaryScreenProvider::class) pagingData: PagingData<SimpleDictionary>
) {
    AppThemeWrapper(ThemeMode.Day) {
        DictionaryScreen(
            state = DictionaryListViewModel.UIState(
                dictionaries = flowOf(pagingData),
                selectedCharacter = null,
                filterMenuExpended = false
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewDictionaryScreenNight(
    @PreviewParameter(DictionaryScreenProvider::class) pagingData: PagingData<SimpleDictionary>
) {
    AppThemeWrapper(ThemeMode.Night) {
        DictionaryScreen(
            state = DictionaryListViewModel.UIState(
                dictionaries = flowOf(pagingData),
                selectedCharacter = null,
                filterMenuExpended = false
            ),
        )
    }
}