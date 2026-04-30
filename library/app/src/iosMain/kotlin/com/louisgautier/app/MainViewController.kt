package com.louisgautier.app

import androidx.compose.ui.window.ComposeUIViewController
import com.louisgautier.app.app.App
import com.louisgautier.firebase.AppleFirebaseManager
import com.louisgautier.firebase.FirebaseManager
import org.koin.compose.KoinApplication
import org.koin.mp.KoinPlatform
import platform.Foundation.NSData
import platform.UIKit.UIViewController

@Suppress("ktlint:standard:function-naming")
fun MainViewController(): UIViewController =
    ComposeUIViewController {
        KoinApplication(application = {
            modules(libraryModule)
        }) {
            App()
        }
    }

@Suppress("unused")
object FirebaseAPNS {
    fun onAPNSReceived(token: NSData) {
        KoinPlatform
            .getKoin()
            .get<FirebaseManager>()
            .let { it as AppleFirebaseManager }
            .onApnsTokenReceived(token)
    }
}
