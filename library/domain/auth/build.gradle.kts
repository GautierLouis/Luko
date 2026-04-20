plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.core.logger)

                implementation(projects.library.data.network)
                implementation(projects.library.data.database)
                implementation(projects.library.data.preferences)
                implementation(projects.library.core.firebase)
            }
        }
    }
}