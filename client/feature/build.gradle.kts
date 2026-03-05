plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.client.feature.login)
                api(projects.client.feature.dictionary)
                api(projects.client.feature.learning)
            }
        }
    }
}
