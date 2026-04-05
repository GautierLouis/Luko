package com.louisgautier.composeApp

import android.content.Context
import com.louisgautier.utils.AppConfig

class AndroidAppBuilder(
    private val context: Context
): AppConfigBuilder {

    override fun invoke(): AppConfig {
        return AppConfig(
            platform = "Android",
            flavor = BuildConfig.FLAVOR,
            isProduction = BuildConfig.FLAVOR == "prod",
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE.toString()
        )
    }
}