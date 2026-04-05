plugins {
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.client.core.logger)
            implementation(projects.client.core.utils)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}