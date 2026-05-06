package com.louisgautier.desktopApp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.louisgautier.app.app.App
import com.louisgautier.app.libraryModule
import org.koin.core.context.startKoin

fun main() =
    application {
        startKoin {
            modules(libraryModule)
        }
        val state = rememberWindowState(width = 300.dp, height = 600.dp)
        Window(
            onCloseRequest = ::exitApplication,
            title = "Learn Chinese",
            state = state
        ) {
            App()
        }
    }
