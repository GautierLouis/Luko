plugins {
    alias(libs.plugins.convention.plugin)
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

    sourceSets {
        commonMain.dependencies {
            implementation(projects.client.core.logger)
            implementation(projects.client.core.utils)
        }

        androidMain.dependencies {
            implementation(projects.client.core.permission)

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
