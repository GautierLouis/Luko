import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.convention.plugin)
    alias(libs.plugins.compose.convention)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            //export(projects.client.core.firebase)
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(projects.client.core)
            implementation(projects.client.feature.login)
            implementation(projects.client.feature.learning)
            implementation(projects.client.feature.dictionary)
            implementation(projects.client.designSystem)
            implementation(projects.client.domain) // needed for Koin
        }

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }

        commonTest.dependencies {
            implementation(libs.junit)
        }
    }
}

android {
    defaultConfig {
        applicationId = "com.louisgautier.composeApp"
        targetSdk = libs.versions.android.target.sdk.get().toInt()
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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}