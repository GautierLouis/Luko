plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.logger)
            implementation(projects.library.core.utils)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
