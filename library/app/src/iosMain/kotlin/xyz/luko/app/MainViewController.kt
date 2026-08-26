package xyz.luko.app

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.mp.KoinPlatform
import platform.Foundation.NSData
import platform.UIKit.UIViewController
import xyz.luko.app.app.App
import xyz.luko.app.app.KoinApp
import xyz.luko.firebase.AppleFirebaseManager
import xyz.luko.firebase.FirebaseManager

@Suppress("unused", "ktlint:standard:function-naming")
fun MainViewController(): UIViewController =
    ComposeUIViewController {
        KoinApp { App() }
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
