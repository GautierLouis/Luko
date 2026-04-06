plugins {
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
            baseName = "App"
            isStatic = true
            //export(projects.client.core.firebase)
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(projects.library.core)
            implementation(projects.library.domain)
            implementation(projects.library.feature)
            implementation(projects.library.designSystem)

            implementation(libs.compose.navigation3.compose)
        }

        commonTest.dependencies {
            implementation(libs.junit)
        }
    }
}