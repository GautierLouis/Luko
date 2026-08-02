package xyz.luko.dictionary.details

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.luko.dictionary.details.ModalCharacterDetailsEvent.OnPractice
import xyz.luko.dictionary.details.ModalCharacterDetailsEvent.OnRetry
import xyz.luko.dictionary.details.ModalCharacterDetailsViewModel.UIState
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.components.page.ErrorContent
import xyz.luko.ui.designsystem.components.page.LoadingContent
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.LoadingMode
import xyz.luko.ui.designsystem.preview.LoadingModeProvider
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.navigation.AppRoute

@Composable
fun CharacterDetailsSheet(
    route: AppRoute.CharacterDetail
) {
    val viewModel =
        koinViewModel<ModalCharacterDetailsViewModel>(parameters = { parametersOf(route.code) })
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterDetailsSheet(
        state = state,
        onEvent = { event ->
            when (event) {
                OnRetry -> viewModel.retry()
                OnPractice -> viewModel.practice()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterDetailsSheet(
    state: UIState,
    onEvent: (ModalCharacterDetailsEvent) -> Unit = {},
) {
    val modalState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )

    // Force partial on Loading/Error, allow full on Success
    LaunchedEffect(state) {
        when (state) {
            is UIState.Loading,
            is UIState.Error,
                -> modalState.partialExpand()

            is UIState.Success -> Unit
        }
    }

    NestedScaffold { _ ->
        when (state) {
            is UIState.Error -> {
                ErrorContent(
                    modifier = Modifier.fillMaxHeight(.5f),
                    action = { onEvent(OnRetry) },
                )
            }

            is UIState.Loading -> {
                LoadingContent(
                    modifier = Modifier.fillMaxHeight(.5f),
                )
            }

            is UIState.Success ->
                DetailsContent(
                    dictionary = state.selectedDictionary,
                    lastSession = state.lastSession,
                    onPractice = {
                        onEvent(OnPractice)
                    },
                )
        }
    }
}

private val successState = UIState.Success(
    selectedDictionary = PreviewProvider.dictionary,
    lastSession = listOf(PreviewProvider.session, PreviewProvider.session),
)

@Preview
@Composable
private fun PreviewCharacterDetailsSheetDay(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode,
) {
    AppTheme(ThemeMode.Day) {
        CharacterDetailsSheet(
            state =
                when (mode) {
                    LoadingMode.LOADING -> UIState.Loading
                    LoadingMode.ERROR -> UIState.Error
                    LoadingMode.SUCCESS -> successState
                },
        )
    }
}

@Preview
@Composable
private fun PreviewCharacterDetailsSheetNight(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode,
) {
    AppTheme(ThemeMode.Night) {
        CharacterDetailsSheet(
            state =
                when (mode) {
                    LoadingMode.LOADING -> UIState.Loading
                    LoadingMode.ERROR -> UIState.Error
                    LoadingMode.SUCCESS -> successState
                },
        )
    }
}
