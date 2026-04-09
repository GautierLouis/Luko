plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.data)
                implementation(projects.library.core.firebase)
            }
        }
    }
}