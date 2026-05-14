package xyz.luko.app.app

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.check.checkModules
import xyz.luko.app.Environment
import xyz.luko.app.libraryModule
import xyz.luko.utils.Flavor
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