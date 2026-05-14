package xyz.luko.utils

class AppConfig(
    val platform: String,
    val flavor: Flavor,
    val versionName: String,
    val versionCode: String,
) {
    val isProduction
        get() = flavor == Flavor.PROD
}

enum class Flavor {
    DEV,
    STAGING,
    PROD,
}
