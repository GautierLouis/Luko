package com.louisgautier.login

import com.louisgautier.auth.authModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    includes(authModule)
    viewModel { LoginViewModel(get()) }
}