plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.compose.navigation3)
            api(libs.compose.navigation3.adaptive)
            implementation(projects.library.core.logger)
        }
    }
}
