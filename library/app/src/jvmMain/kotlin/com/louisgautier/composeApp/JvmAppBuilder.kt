package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig

class JvmAppBuilder: AppConfigBuilder {

    override fun invoke(): AppConfig {
        return AppConfig(
            platform = "Desktop",
            flavor = "prod",
            isProduction = true,
            versionName = "1.0.0",
            versionCode = "1.0.0"
        )
    }
}