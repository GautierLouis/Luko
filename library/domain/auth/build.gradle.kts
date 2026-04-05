plugins {
    alias(libs.plugins.convention.plugin)
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