package com.louisgautier.app

import androidx.compose.ui.window.ComposeUIViewController
import com.louisgautier.app.app.App
import org.koin.compose.KoinApplication
import platform.UIKit.UIViewController

@Suppress("unused")
fun MainViewController(): UIViewController =
    ComposeUIViewController {
        KoinApplication(application = {
            modules(libraryModule)
        }) {
            App()
        }
    }
