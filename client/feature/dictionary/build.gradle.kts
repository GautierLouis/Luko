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
            implementation(projects.client.core.logger)
            implementation(projects.client.core.utils)
            implementation(projects.client.core.navigation)
            implementation(projects.client.domain)
            implementation(projects.client.designSystem)

            implementation(libs.androidx.paging.common)
            implementation(libs.androidx.paging.compose)
        }
    }
}