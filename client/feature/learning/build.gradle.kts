plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.plugin)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.client.core.logger)
            implementation(projects.client.core.utils)
            implementation(projects.client.designSystem)
            implementation(projects.client.domain)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}