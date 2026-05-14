package xyz.luko.dictionary

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.dictionary.details.ModalCharacterDetailsViewModel
import xyz.luko.dictionary.home.DictionaryListViewModel

val dictionaryModule =
    module {
        viewModelOf(::DictionaryListViewModel)
        viewModelOf(::ModalCharacterDetailsViewModel)
    }
