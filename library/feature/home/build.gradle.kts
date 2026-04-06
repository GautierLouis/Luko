plugins {
    alias(libs.plugins.convention.plugin)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.logger)
            implementation(projects.library.core.utils)
            implementation(projects.library.core.navigation)
            implementation(projects.library.domain)
            implementation(projects.library.designSystem)
        }
    }
}