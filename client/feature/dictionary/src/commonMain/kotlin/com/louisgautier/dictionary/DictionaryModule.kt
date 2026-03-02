package com.louisgautier.dictionary

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dictionaryModule = module {
    viewModelOf(::DictionaryListViewModel)
}