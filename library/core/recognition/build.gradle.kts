plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.utils)
        }

        androidMain.dependencies {
            implementation(libs.digital.ink.recognition)
            implementation(libs.kotlinx.coroutines.play.services)
        }
    }
}
