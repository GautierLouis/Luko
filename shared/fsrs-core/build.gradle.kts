plugins {
    alias(libs.plugins.multiplatform.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.apiContracts)
        }
    }
}
