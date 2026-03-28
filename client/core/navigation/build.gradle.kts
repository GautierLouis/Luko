plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.navigation3.compose)
        }
    }
}