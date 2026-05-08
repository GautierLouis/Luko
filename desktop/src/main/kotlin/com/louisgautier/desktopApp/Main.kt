package com.louisgautier.desktopApp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.louisgautier.app.app.App
import com.louisgautier.app.libraryModule
import org.koin.core.context.startKoin

fun main() =
    application {
        startKoin {
            modules(libraryModule)
        }
        Window(
            onCloseRequest = ::exitApplication,
            title = "Learn Chinese",
        ) {
            App()
        }
    }