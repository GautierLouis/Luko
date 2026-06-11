package xyz.luko.app.main

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

internal class MainViewModel : ViewModel() {

    data class UiState(
        val enableBottomBar: Boolean = false,
        val leadingMenuItems: ImmutableList<MenuItem>,
        val trailingMenuItems: ImmutableList<MenuItem>,
        val selectedItem: MenuItem,
    )

    private val _state =
        MutableStateFlow(
            value =
                UiState(
                    selectedItem = MenuItem.Home,
                    leadingMenuItems = persistentListOf(
//                        MenuItem.Home,
                    ),
                    trailingMenuItems = persistentListOf(
//                        MenuItem.Dictionary
                    )
                ),
        )
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onEventReceived(event: MainScaffoldEvent) =
        when (event) {
            is MainScaffoldEvent.OnItemClick -> updateBottomItem(event.item)
            is MainScaffoldEvent.OnMainItemClick -> {
                AppNavigation.navigate(
                    route = AppRoute.LearningRoute.NewSessionRoute,
                    clearBackStack = true
                )
            }
        }

    private fun updateBottomItem(item: MenuItem) {
        Tracker.track(TrackingEvent.NavigateTo(item.toString()))
        _state.update { it.copy(selectedItem = item) }
    }
}
