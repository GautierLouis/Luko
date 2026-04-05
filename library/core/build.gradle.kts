plugins {
    alias(libs.plugins.convention.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.library.core.utils)
            api(projects.library.core.logger)
            api(projects.library.core.permission)
            api(projects.library.core.firebase)
            api(projects.library.core.navigation)
        }
    }
}