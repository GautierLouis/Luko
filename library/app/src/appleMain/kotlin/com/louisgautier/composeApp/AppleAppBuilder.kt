package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig
import platform.Foundation.NSBundle

class AppleAppBuilder: AppConfigBuilder {

    override fun invoke(): AppConfig {
        return AppConfig(
            platform = "Apple",
            flavor = "prod",
            isProduction = true,
            versionName = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknow",
            versionCode = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknow"
        )
    }
}