import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FirebaseModule"
            isStatic = true
        }
    }

    swiftPMDependencies {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        swiftPackage(
            url = url("https://github.com/firebase/firebase-ios-sdk.git"),
            version = from("12.5.0"),
            products = listOf(
                product("FirebaseAnalytics"),
                product("FirebaseMessaging"),
                product("FirebaseAuth"),
                product("FirebaseRemoteConfig"),
                product("FirebaseCore"),
                product("FirebaseInstallations")
            )
        )
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.logger)
            implementation(projects.library.core.utils)
            implementation(projects.library.core.tracking)
            implementation(projects.library.core.permission)
        }

        androidMain.dependencies {

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.config)
            implementation(libs.firebase.messaging)
            implementation(libs.firebase.auth)
            implementation(libs.androidx.work.runtime.ktx)

            implementation(libs.kotlinx.coroutines.play.services)
        }
    }
}
