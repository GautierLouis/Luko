package xyz.luko.app.main

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

internal class MainViewModel : ViewModel() {

    data class UiState(
        val menuItems: ImmutableList<MenuItem>,
        val selectedItem: MenuItem,
    )

    val state: StateFlow<UiState>
        field = MutableStateFlow(
            value =
                UiState(
                    selectedItem = MenuItem.Home,
                    menuItems = persistentListOf(
                        MenuItem.Home,
                        MenuItem.Dictionary,
                        MenuItem.Feed,
                        MenuItem.Profile,
                    ),
                ),
        )

    fun onEventReceived(event: MainScaffoldEvent) =
        when (event) {
            is MainScaffoldEvent.OnItemClick -> updateBottomItem(event.item)
            is MainScaffoldEvent.OnMainItemClick -> {
                AppNavigation.navigate(
                    route = AppRoute.Learning.BuildSession,
                    clearBackStack = true
                )
            }
        }

    private fun updateBottomItem(item: MenuItem) {
        Tracker.track(TrackingEvent.NavigateTo(item.toString()))
        state.update { it.copy(selectedItem = item) }
    }
}
