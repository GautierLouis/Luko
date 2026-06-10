plugins {
    alias(libs.plugins.multiplatform.convention)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.domain)
            implementation(projects.library.core.utils)
            implementation(projects.library.feature.ui.designSystem)
            implementation(projects.library.feature.ui.core)
        }
    }
}
