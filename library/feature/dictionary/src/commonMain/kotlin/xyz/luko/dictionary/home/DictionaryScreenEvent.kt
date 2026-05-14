package xyz.luko.dictionary.home

internal sealed class DictionaryScreenEvent {
    data class OnCharacterClicked(
        val code: Int,
    ) : DictionaryScreenEvent()

    data object OnSearch : DictionaryScreenEvent()

    data object OnFilterToggle : DictionaryScreenEvent()

    data class OnFilterChange(
        val activeFilter: ActiveFilter,
    ) : DictionaryScreenEvent()

    data object OnModalDismiss : DictionaryScreenEvent()
}
