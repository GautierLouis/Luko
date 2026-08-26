package xyz.luko.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import org.koin.android.ext.android.inject
import org.koin.core.annotation.KoinExperimentalAPI
import xyz.luko.app.ActivityObserver
import xyz.luko.app.app.App
import xyz.luko.app.app.KoinApp

class MainActivity : FragmentActivity() {

    private val activityObserver: ActivityObserver by inject()

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            activityObserver.setIntentResult(result)
        }

        val permissionLauncher: ActivityResultLauncher<Array<String>> = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result: Map<String, @JvmSuppressWildcards Boolean> ->
            activityObserver.setPermissionResult(result)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            KoinApp(platformModules = listOf(androidModule)) {
                activityObserver.setIntentLauncher(activityResultLauncher)
                activityObserver.setPermissionLauncher(permissionLauncher)
                App()
            }
        }
    }
}
