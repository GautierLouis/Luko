package com.louisgautier.dictionary.home

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.dictionary.details.ModalCharacterDetails
import com.louisgautier.dictionary.details.ModalCharacterDetailsViewModel
import com.louisgautier.dictionary.home.DictionaryScreenEvent.OnCharacterClicked
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

    val contentTopCorner = if (state.filterMenuExpended) Padding.extraLarge
    else Padding.none

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

    Scaffold(
        topBar = {
            DictionaryTopBar(
                textFieldState = state.textFieldState,
                filterMenuExpended = state.filterMenuExpended,
                activeFilter = state.activeFilter,
            ) { onEvent(it) }
        },
        containerColor = Theme.materialColors.surfaceContainer
    ) { paddingValues ->
        DictionaryPage(
            items = items,
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                )
                .background(
                    color = Theme.materialColors.background,
                    shape = RoundedCornerShape(
                        topStart = contentTopCorner,
                        topEnd = contentTopCorner
                    )
                ),
            onItemClick = {
                onEvent(OnCharacterClicked(it))
            }
        )
    }
}

@VisibleForTesting
@Preview
@Composable
fun PreviewDictionaryScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        DictionaryScreen(
            state = DictionaryListViewModel.UIState(
                dictionaries = flowOf(PagingData.from(previewSimpleDataList)),
                selectedCharacter = null,
                filterMenuExpended = false
            )
        )
    }
}