plugins {
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.client.domain.auth)
                implementation(projects.client.core.utils)
                implementation(projects.client.data)

                implementation(libs.androidx.paging.common)
                implementation(libs.androidx.paging.compose)
            }
        }
    }
}
