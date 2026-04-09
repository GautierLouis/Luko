plugins {
    alias(libs.plugins.multiplatform.convention)
    alias(libs.plugins.compose.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.navigation)
            implementation(projects.library.core.utils)
        }
    }
}