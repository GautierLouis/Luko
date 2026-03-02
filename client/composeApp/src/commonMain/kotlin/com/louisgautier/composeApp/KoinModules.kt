package com.louisgautier.composeApp

import com.louisgautier.composeApp.home.HomeViewModel
import com.louisgautier.core.coreModule
import com.louisgautier.dictionary.dictionaryModule
import com.louisgautier.domain.domainModule
import com.louisgautier.learning.learningModule
import com.louisgautier.login.loginModule
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.context.ContextWrapper
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun getAllModules(): List<Module> = domainModule + featuresModule() + coreModule + appModule()

private fun featuresModule() = listOf(
    loginModule,
    learningModule,
    dictionaryModule,
)

private fun appModule() = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AppViewModel)
    single { buildPlatformFlavor(get()) }
}

expect fun buildPlatformFlavor(contextWrapper: ContextWrapper): AppConfig