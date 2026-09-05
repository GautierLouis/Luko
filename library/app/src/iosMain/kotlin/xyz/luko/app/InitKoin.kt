package xyz.luko.app

import io.kotzilla.generated.monitoring
import org.koin.core.context.startKoin

/**
 * Utility function to start Koin from iOS
 */
fun initKoinIos() {
    startKoin {
        modules(libraryModule) //will include libraryPlatformModule
        monitoring()
    }
}
