package com.louisgautier.dictionary.home

import com.louisgautier.dictionary.home.ActiveFilter

internal sealed class DictionaryScreenEvent {
    data class OnCharacterClicked(val code: Int) : DictionaryScreenEvent()
    data object OnSearch : DictionaryScreenEvent()
    data object OnFilterToggle : DictionaryScreenEvent()
    data class OnFilterChange(val activeFilter: ActiveFilter) : DictionaryScreenEvent()
    data object OnModalDismiss : DictionaryScreenEvent()
}