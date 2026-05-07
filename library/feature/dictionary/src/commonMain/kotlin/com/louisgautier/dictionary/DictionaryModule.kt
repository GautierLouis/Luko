package com.louisgautier.dictionary

import com.louisgautier.dictionary.details.ModalCharacterDetailsViewModel
import com.louisgautier.dictionary.home.DictionaryListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dictionaryModule =
    module {
        viewModelOf(::DictionaryListViewModel)
        viewModelOf(::ModalCharacterDetailsViewModel)
    }
