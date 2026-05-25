package xyz.luko.server.plugin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.Application

class FirebasePlugin : Plugin {

    override fun Application.register() {
        val credentials = when {
            // Production — from env var
            System.getenv("FIREBASE_SERVICE_ACCOUNT") != null -> {
                val json = System.getenv("FIREBASE_SERVICE_ACCOUNT")
                GoogleCredentials.fromStream(json.byteInputStream())
            }
            // Local dev — from file
            else -> {
                val stream = Application::class.java
                    .getResourceAsStream("/service-account.json")
                    ?: error("service-account.json not found in resources")
                GoogleCredentials.fromStream(stream)
            }
        }

        FirebaseApp.initializeApp(
            FirebaseOptions.builder()
                .setCredentials(credentials)
                .build()
        )
    }
}
