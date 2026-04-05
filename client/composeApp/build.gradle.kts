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
            baseName = "ComposeApp"
            isStatic = true
            //export(projects.client.core.firebase)
        }
    }

    sourceSets {

        commonMain.dependencies {
            implementation(projects.client.core)
            implementation(projects.client.domain)
            implementation(projects.client.feature)
            implementation(projects.client.designSystem)

            implementation(libs.compose.navigation3.compose)
        }

        androidMain.dependencies {
            api(projects.client.core.utils)
        }

        commonTest.dependencies {
            implementation(libs.junit)
        }
    }
}