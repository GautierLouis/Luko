import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.compose.compiler)
}

android {
    defaultConfig {
        applicationId = "com.louisgautier.composeApp"
        namespace = "com.louisgautier.composeApp" //TODO TO be changed
        compileSdk = libs.versions.android.target.sdk.get().toInt()
        targetSdk = libs.versions.android.target.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.asProvider().get()
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "(Dev) Learn Chinese")
        }

        create("staging") {
            dimension = "environment"

            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            resValue("string", "app_name", "(Staging) Learn Chinese")
        }

        create("prod") {
            dimension = "environment"
            resValue("string", "app_name", "Learn Chinese")
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