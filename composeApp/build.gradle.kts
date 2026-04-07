import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.compose.compiler)
}

object Flavors {
    const val DEV = "dev"
    const val STAGING = "staging"
    const val PROD = "prod"
}

data class FlavorConfig(
    val name: String,
    val alias: String? = null,
)

val flavors = listOf(
    FlavorConfig(Flavors.DEV, "Dev"),
    FlavorConfig(Flavors.STAGING, "Staging"),
    FlavorConfig(Flavors.PROD)
)

android {
    namespace = "com.louisgautier.composeApp"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.louisgautier.composeApp" //TODO To be changed
        targetSdk = libs.versions.android.target.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.asProvider().get()
    }

    flavorDimensions += "environment"
    productFlavors {
        flavors.forEach { flavor ->
            create(flavor.name) {
                dimension = "environment"
                flavor.alias?.let {
                    applicationIdSuffix = ".${flavor.name}"
                    versionNameSuffix = "-${flavor.name}"
                    buildConfigField("string", "app_name", "(${flavor.alias}) Learn Chinese")
                }
                buildConfigField("String", "ENVIRONMENT", "\"${flavor.name}\"")
            }
        }
    }

    signingConfigs {
        create("release") {
            val props = Properties()
            file("keystore.properties").inputStream().use { props.load(it) }

            storeFile = file(props["keyStoreFile"] as String)
            storePassword = props["keyStorePassword"] as String
            keyAlias = props["keyAlias"] as String
            keyPassword = props["keyPassword"] as String
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.library.app)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}