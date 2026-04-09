plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.library.data.network)
                api(projects.library.data.database)
                api(projects.library.data.preferences)
            }
        }
    }
}
