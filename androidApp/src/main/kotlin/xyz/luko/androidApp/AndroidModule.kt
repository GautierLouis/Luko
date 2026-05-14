package xyz.luko.androidApp

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.luko.app.Environment

val androidModule = module {
    single(named(Environment.FLAVOR)) {
        BuildConfig.FLAVOR.uppercase()
    }

    single(named(Environment.PLATFORM)) {
        Environment.ANDROID
    }
    single(named(Environment.VERSION_CODE)) {
        BuildConfig.VERSION_CODE.toString()
    }
    single(named(Environment.VERSION_NAME)) {
        BuildConfig.VERSION_NAME
    }
}