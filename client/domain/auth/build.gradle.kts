plugins {
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.client.data)
                implementation(projects.client.core.firebase)
            }
        }
    }
}