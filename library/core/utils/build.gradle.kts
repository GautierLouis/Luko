plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
