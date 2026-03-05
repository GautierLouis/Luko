package com.louisgautier.composeApp

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.KoinApplication
import platform.UIKit.UIViewController

@Suppress("unused")
fun MainViewController(): UIViewController =
    ComposeUIViewController {
        KoinApplication(application = {
            modules(appModule)
        }) {
            App()
        }
    }
