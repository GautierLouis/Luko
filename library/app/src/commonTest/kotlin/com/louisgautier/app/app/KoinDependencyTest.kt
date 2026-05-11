package com.louisgautier.app.app

import androidx.lifecycle.SavedStateHandle
import com.louisgautier.app.Environment
import com.louisgautier.app.libraryModule
import com.louisgautier.utils.Flavor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.check.checkModules
import kotlin.test.Test

class KoinDependencyTest {

    val clientModule: Module = module {
        single(named(Environment.FLAVOR)) { Flavor.DEV }
        single(named(Environment.PLATFORM)) { Environment.ANDROID }
        single(named(Environment.VERSION_CODE)) { "1.0.0" }
        single(named(Environment.VERSION_NAME)) { "100" }
        single { 1 } //bad
        single { SavedStateHandle() } //bad
    }

    @Test
    fun `assert Koin graph contains all dependencies`() {
        koinApplication {
            modules(
                libraryModule,
                clientModule,
            )
        }.checkModules() // no verify on kmp
    }
}