import org.jetbrains.compose.resources.ResourcesExtension

plugins {
    alias(libs.plugins.multiplatform.convention)
    alias(libs.plugins.compose.convention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.library.core.navigation)
            implementation(projects.library.core.utils)
            implementation(libs.compose.components.resources)
        }
    }
}

compose.resources {
    generateResClass = ResourcesExtension.ResourceClassGeneration.Always
    packageOfResClass = "luko.library.design_system.generated.resources"
    publicResClass = true
}
