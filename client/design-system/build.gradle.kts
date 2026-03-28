plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.plugin)
    alias(libs.plugins.compose.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.client.core.navigation)
            implementation(projects.client.core.utils)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
