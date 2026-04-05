plugins {
    alias(libs.plugins.convention.plugin)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(compose.uiTooling)
        }
        commonMain.dependencies {
            implementation(projects.library.core.logger)
            implementation(projects.library.core.utils)
            implementation(projects.library.core.navigation)
            implementation(projects.library.domain)
            implementation(projects.library.designSystem)

            implementation(libs.androidx.paging.common)
            implementation(libs.androidx.paging.compose)
        }
    }
}