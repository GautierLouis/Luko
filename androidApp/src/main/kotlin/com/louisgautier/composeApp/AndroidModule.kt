package com.louisgautier.composeApp

import com.louisgautier.app.BuildConfig
import com.louisgautier.app.Environment
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun createAndroidModule() = module {
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