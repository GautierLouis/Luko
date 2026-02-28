package com.louisgautier.composeApp

import com.louisgautier.composeApp.dictionary.DictionaryListViewModel
import com.louisgautier.composeApp.drawing.legacy.SVGViewModel
import com.louisgautier.composeApp.home.HomeViewModel
import com.louisgautier.composeApp.session.SessionBuilderViewModel
import com.louisgautier.composeApp.session.SessionCongratulationViewModel
import com.louisgautier.composeApp.session.SessionViewModel
import com.louisgautier.core.coreModule
import com.louisgautier.domain.domainModule
import com.louisgautier.login.loginModule
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.context.ContextWrapper
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun getAllModules(): List<Module> = domainModule + featuresModule() + coreModule + appModule()

private fun featuresModule() = listOf(
    loginModule
)

private fun appModule() = module {
    viewModelOf(::SVGViewModel)
    viewModelOf(::DictionaryListViewModel)
    viewModelOf(::SessionBuilderViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SessionCongratulationViewModel)
    viewModelOf(::AppViewModel)
    single { buildPlatformFlavor(get()) }
}

expect fun buildPlatformFlavor(contextWrapper: ContextWrapper): AppConfig