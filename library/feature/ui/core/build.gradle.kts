plugins {
    alias(libs.plugins.multiplatform.convention)
    alias(libs.plugins.compose.convention)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.domain)

            implementation(libs.androidx.paging.common)
            implementation(libs.androidx.paging.compose)
        }
    }
}
