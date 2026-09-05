package xyz.luko.android

import android.app.Application
import io.kotzilla.generated.monitoring
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import xyz.luko.app.libraryModule

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(libraryModule, androidModule)
            monitoring()
        }
    }
}
