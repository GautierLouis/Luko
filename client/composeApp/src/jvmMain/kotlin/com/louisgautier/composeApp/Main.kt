package com.louisgautier.composeApp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.louisgautier.composeApp.app.App
import org.koin.core.context.startKoin

fun main() =
    application {
        startKoin {
            modules(appModule)
        }
        Window(
            onCloseRequest = ::exitApplication,
            title = "Learn Chinese",
        ) {
            App()
        }
    }
