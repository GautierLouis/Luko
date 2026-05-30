plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kermit)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
