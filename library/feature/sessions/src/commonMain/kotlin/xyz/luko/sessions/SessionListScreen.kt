package xyz.luko.sessions

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole.Detail
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole.List
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.sessions.PaneNavigationEvent.NavigateBack
import xyz.luko.sessions.PaneNavigationEvent.NavigateToDetails
import xyz.luko.sessions.PaneNavigationEvent.NavigateToExtra
import xyz.luko.sessions.component.SessionDetailsPane
import xyz.luko.sessions.component.SessionExtraPane
import xyz.luko.sessions.component.SessionListPane
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SessionListScreen(
    route: AppRoute.SessionsRoute.SessionListRoute
) {
    val viewModel = koinViewModel<SessionListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigator = rememberListDetailPaneScaffoldNavigator<Long>(
        initialDestinationHistory = when {
            route.id != null -> listOf(ThreePaneScaffoldDestinationItem(Detail))
            else -> listOf(ThreePaneScaffoldDestinationItem(List))
        }
    )
    val lazyColumState = rememberLazyListState()
    val backState = rememberNavigationEventState(currentInfo = NavigationEventInfo.None)

    LaunchedEffect(route.id, state.sessions) {
        val canShowDetail =
            navigator.scaffoldValue[Detail] == PaneAdaptedValue.Expanded
        viewModel.initView(route.id, canShowDetail)
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is NavigateBack -> navigator.navigateBack()

                is NavigateToExtra -> {
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Extra,
                        contentKey = event.responseCode.toLong()
                    )
                }

                is NavigateToDetails -> {
                    event.scrollPosition?.let {
                        lazyColumState.scrollToItem(event.scrollPosition)
                    }

                    navigator.navigateTo(
                        pane = Detail,
                        contentKey = event.sessionId
                    )
                }
            }
        }
    }

    NavigationBackHandler(
        state = backState,
        isBackEnabled = navigator.canNavigateBack(),
        onBackCompleted = {
            if (!navigator.canNavigateBack()) {
                AppNavigation.navigateUp()
            } else viewModel.navigateBack()
        },
    )


    NestedScaffold {
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            modifier = Modifier,
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
}


