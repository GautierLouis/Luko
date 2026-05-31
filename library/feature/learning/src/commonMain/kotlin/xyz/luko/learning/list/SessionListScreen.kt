package xyz.luko.learning.list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.navigation.AppRoute

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SessionListScreen(
    route: AppRoute.LearningRoute.SessionListRoute
) {
    val viewModel = koinViewModel<SessionListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val backState = rememberNavigationEventState(currentInfo = NavigationEventInfo.None)
    val lazyColumState = rememberLazyListState()

    LaunchedEffect(route.id, state.sessions) {
        if (route.id != null && state.sessions.isNotEmpty()) {
            val index = state.sessions.indexOfFirst { it.id == route.id }
            if (index >= 0) lazyColumState.scrollToItem(index)
            viewModel.onSessionSelected(route.id!!)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is PaneNavigationEvent.NavigateBack -> navigator.navigateBack()
                is PaneNavigationEvent.NavigateToExtra ->
                    navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Extra,
                        event.responseCode.toLong()
                    )

                is PaneNavigationEvent.NavigateToDetails -> {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, event.sessionId)
                }
            }
        }
    }

    NavigationBackHandler(
        state = backState,
        isBackEnabled = navigator.canNavigateBack(),
        onBackCompleted = { viewModel.navigateBack() },
    )

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                SessionListPane(
                    state = state,
                    lazyColumState = lazyColumState,
                    onClick = {
                        viewModel.onSessionSelected(it)
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                SessionDetailsPane(
                    responses = state.responses,
                    onClick = {
                        viewModel.onResponseSelected(it)
                    }
                )
            }
        },
        extraPane = {
            AnimatedPane {
                state.selectedResponse?.let {
                    SessionExtraPane(
                        response = state.selectedResponse!!,
                        similarResponse = state.similarResponses
                    )
                }
            }
        }
    )
}


