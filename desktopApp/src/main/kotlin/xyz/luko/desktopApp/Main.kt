package xyz.luko.desktopApp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import xyz.luko.app.app.App
import xyz.luko.app.libraryModule

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