plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.library.domain.auth)
                implementation(projects.library.core.utils)
                implementation(projects.library.data)

                implementation(libs.androidx.paging.common)
                implementation(libs.androidx.paging.compose)
            }
        }
    }
}
