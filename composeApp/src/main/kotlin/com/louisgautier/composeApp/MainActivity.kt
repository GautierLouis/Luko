package com.louisgautier.composeApp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.louisgautier.app.ActivityObserver
import com.louisgautier.app.app.App
import com.louisgautier.app.libraryModule
import org.koin.android.ext.android.inject
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

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
            KoinMultiplatformApplication(
                config = koinConfiguration {
                    modules(libraryModule, androidModule)
                }
            ) {
                activityObserver.setIntentLauncher(activityResultLauncher)
                activityObserver.setPermissionLauncher(permissionLauncher)
                App()
            }
        }
    }
}