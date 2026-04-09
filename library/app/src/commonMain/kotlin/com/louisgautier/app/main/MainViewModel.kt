package com.louisgautier.app.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.navigation.AppRoute
import com.louisgautier.navigation.MainTab
import com.louisgautier.tracking.Tracker
import com.louisgautier.tracking.TrackingEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class MainViewModel(
    savedStateHandle: SavedStateHandle,
    remoteConfigManager: RemoteConfigManager
) : ViewModel() {

    data class UiState(
        val enableBottomBar: Boolean = false,
        val bottomNavItem: ImmutableList<BottomNavItem> = BottomNavItem.entries.toImmutableList(),
        val selectedItem: BottomNavItem
    )

    private val descriptor = AppRoute.MainRoute.serializer().descriptor
    val defaultTab: MainTab = savedStateHandle[descriptor.getElementName(0)]
        ?: MainTab.Home

    val defaultSelectedItem
        get() = when (defaultTab) {
            MainTab.Dictionary -> BottomNavItem.Dictionary
            MainTab.Profile -> BottomNavItem.Profile
            MainTab.Feed -> BottomNavItem.Feed
            else -> BottomNavItem.Home
        }

    private val _state = MutableStateFlow(
        value = UiState(
            selectedItem = defaultSelectedItem,
            enableBottomBar = remoteConfigManager.synchronizedFlags.isBottomBarEnabled
        )
    )
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onEventReceived(event: MainScaffoldEvent) = when (event) {
        is MainScaffoldEvent.OnBottomItemClicked -> updateBottomItem(event.item)
    }

    private fun updateBottomItem(item: BottomNavItem) {
        Tracker.track(TrackingEvent.NavigateTo(item.toString()))
        _state.update { it.copy(selectedItem = item) }
    }
}