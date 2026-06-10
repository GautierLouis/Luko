plugins {
    alias(libs.plugins.multiplatform.convention)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.utils)
            implementation(projects.library.feature.ui.navigation)
            implementation(projects.library.domain)
            implementation(projects.library.feature.ui.designSystem)
        }
    }
}
