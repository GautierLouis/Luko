package xyz.luko.sessions

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sessionsModule =
    module {
        viewModelOf(::SessionListViewModel)
    }
