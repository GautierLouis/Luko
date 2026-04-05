package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig
import platform.Foundation.NSBundle

class IOSAppBuilder : AppConfigBuilder {

    override fun invoke(): AppConfig {
        return AppConfig(
            platform = "iOS",
            flavor = "prod",
            isProduction = true,
            versionName = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknow",
            versionCode = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknow"
        )
    }
}