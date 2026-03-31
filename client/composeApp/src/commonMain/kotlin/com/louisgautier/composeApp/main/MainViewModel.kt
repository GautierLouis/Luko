package com.louisgautier.composeApp.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.louisgautier.navigation.MainKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    data class UiState(
        val bottomNavItem: ImmutableList<BottomNavItem> = BottomNavItem.entries.toImmutableList(),
        val selectedItem: BottomNavItem
    )

    private val descriptor = MainKey.serializer().descriptor
    val defaultSelectedItemIndex: Int = savedStateHandle[descriptor.getElementName(0)]
        ?: 0

    val defaultSelectedItem
        get() = BottomNavItem.entries[defaultSelectedItemIndex]

    private val _state = MutableStateFlow(UiState(selectedItem = defaultSelectedItem))
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onEventReceived(event: MainScreenEvent) = when (event) {
        is MainScreenEvent.OnBottomItemClicked -> updateBottomItem(event.item)
    }

    private fun updateBottomItem(item: BottomNavItem) {
        _state.update { it.copy(selectedItem = item) }
    }
}