package com.louisgautier.dictionary.details

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.page.ErrorContent
import com.louisgautier.designsystem.components.page.LoadingContent
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.LoadingMode
import com.louisgautier.designsystem.preview.LoadingModeProvider
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.dictionary.details.ModalCharacterDetailsEvent.OnPractice
import com.louisgautier.dictionary.details.ModalCharacterDetailsEvent.OnRetry
import com.louisgautier.dictionary.details.ModalCharacterDetailsViewModel.UIState
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.domain.previewSession
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun ModalCharacterDetails(
    viewModel: ModalCharacterDetailsViewModel,
    onDismiss: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ModalCharacterDetails(
        state = state,
        onDismiss = onDismiss,
        onEvent = { event ->
            when (event) {
                OnRetry -> viewModel.retry()
                OnPractice -> viewModel.practice()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ModalCharacterDetails(
    state: UIState,
    onEvent: (ModalCharacterDetailsEvent) -> Unit = {},
    onDismiss: () -> Unit = {},
) {

    val modalState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    // Force partial on Loading/Error, allow full on Success
    LaunchedEffect(state) {
        when (state) {
            is UIState.Loading,
            is UIState.Error -> modalState.partialExpand()
            is UIState.Success -> Unit
        }
    }
    ModalBottomSheet(
        modifier = Modifier,
        sheetState = modalState,
        containerColor = Theme.materialColors.background,
        shape = ShapeDefaults.bottomSheet(),
        scrimColor = Theme.materialColors.scrim,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Theme.materialColors.onBackground
            )
        },
        onDismissRequest = onDismiss,
        content = {
            Scaffold(
                containerColor = Theme.materialColors.background,
                contentColor = Theme.materialColors.onBackground
            ) { paddingValues ->
                when (state) {
                    is UIState.Error -> {
                        ErrorContent(
                            modifier = Modifier.fillMaxHeight(.5f),
                            action = { onEvent(OnRetry) }
                        )
                    }

                    is UIState.Loading -> {
                        LoadingContent(
                            modifier = Modifier.fillMaxHeight(.5f)
                        )
                    }

                    is UIState.Success -> DetailsContent(
                        dictionaryWithGraphic = state.selectedDictionary,
                        lastSession = state.lastSession,
                        onPractice = {
                            onEvent(OnPractice)
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewModalCharacterDetailsDay(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode
) {
    AppThemeWrapper(ThemeMode.Day) {
        ModalCharacterDetails(
            state = when (mode) {
                LoadingMode.LOADING -> UIState.Loading
                LoadingMode.ERROR -> UIState.Error
                LoadingMode.SUCCESS -> {
                    UIState.Success(
                        selectedDictionary = previewDictionaryWithGraphic,
                        lastSession = listOf(previewSession, previewSession)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun PreviewModalCharacterDetailsNight(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode
) {
    AppThemeWrapper(ThemeMode.Night) {
        ModalCharacterDetails(
            state = when (mode) {
                LoadingMode.LOADING -> UIState.Loading
                LoadingMode.ERROR -> UIState.Error
                LoadingMode.SUCCESS -> {
                    UIState.Success(
                        selectedDictionary = previewDictionaryWithGraphic,
                        lastSession = listOf(previewSession, previewSession)
                    )
                }
            }
        )
    }
}
