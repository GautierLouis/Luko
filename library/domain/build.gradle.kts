plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.core.utils)
                implementation(projects.library.data.network)
                implementation(projects.library.data.database)
                implementation(projects.library.data.preferences)

                implementation(libs.androidx.paging.common)
                implementation(libs.androidx.paging.compose)
            }
        }
    }
}
