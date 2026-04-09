plugins {
    alias(libs.plugins.multiplatform.convention)
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
            implementation(projects.library.core.utils)
            implementation(projects.library.core.logger)
            implementation(projects.library.core.firebase)
            implementation(projects.library.core.permission)
            implementation(projects.library.core.navigation)
            implementation(projects.library.core.tracking)

            implementation(projects.library.domain)
            implementation(projects.library.domain.auth)

            implementation(projects.library.feature.home)
            implementation(projects.library.feature.feed)
            implementation(projects.library.feature.login)
            implementation(projects.library.feature.profile)
            implementation(projects.library.feature.learning)
            implementation(projects.library.feature.dictionary)

            implementation(projects.library.designSystem)
        }

        commonTest.dependencies {
            implementation(libs.junit)
        }
    }
}