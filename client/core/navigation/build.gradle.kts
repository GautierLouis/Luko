plugins {
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.compose.navigation3.compose)
        }
    }
}