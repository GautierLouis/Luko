plugins {
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.library.feature.home)
                api(projects.library.feature.login)
                api(projects.library.feature.profile)
                api(projects.library.feature.learning)
                api(projects.library.feature.dictionary)
            }
        }
    }
}
