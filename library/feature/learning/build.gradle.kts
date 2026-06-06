plugins {
    alias(libs.plugins.multiplatform.convention)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.feature.ui.core)
            implementation(projects.library.feature.baseUi)
            implementation(projects.library.feature.ui.drawing)
            implementation(projects.library.core.utils)
            implementation(projects.library.core.tracking)
            implementation(projects.library.core.navigation)
            implementation(projects.library.designSystem)
            implementation(projects.library.domain)
        }
    }
}
