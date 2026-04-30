package com.louisgautier.utils

class AppConfig(
    val platform: String,
    val flavor: Flavor,
    val isProduction: Boolean,
    val versionName: String,
    val versionCode: String,
)

enum class Flavor {
    DEV,
    STAGING,
    PROD,
}
