package com.louisgautier.feed

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val feedModule =
    module {
        viewModelOf(::FeedViewModel)
    }
