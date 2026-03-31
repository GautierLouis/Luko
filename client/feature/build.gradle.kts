plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.client.feature.login)
                api(projects.client.feature.profile)
                api(projects.client.feature.learning)
                api(projects.client.feature.dictionary)
            }
        }
    }
}
