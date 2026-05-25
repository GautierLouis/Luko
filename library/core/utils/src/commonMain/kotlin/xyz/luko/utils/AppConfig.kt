package xyz.luko.utils

class AppConfig(
    val platform: String,
    val flavor: Flavor,
    val versionName: String,
    val versionCode: String,
    val baseUrl: String = when (flavor) {
        Flavor.DEV -> "https://unethical-thing-gush.ngrok-free.dev"
        Flavor.STAGING -> "https://staging-api.lukoapp.xyz"
        Flavor.PROD -> "https://api.lukoapp.xyz"
    }
) {
    val isProduction
        get() = flavor == Flavor.PROD
}

enum class Flavor {
    DEV,
    STAGING,
    PROD,
}
