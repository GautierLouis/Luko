package xyz.luko.app.main

internal sealed class MainScaffoldEvent {
    data class OnItemClick(
        val item: MenuItem,
    ) : MainScaffoldEvent()

    data object OnMainItemClick : MainScaffoldEvent()
}
