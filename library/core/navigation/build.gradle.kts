plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.compose.navigation3)
        }
    }
}