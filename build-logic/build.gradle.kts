import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(files(gradle.serviceOf<DependenciesAccessors>().classes.asFiles))
    compileOnly(libs.android.gradle.get())
    compileOnly(libs.kotlin.gradle.get())
    compileOnly(libs.kotlin.multiplatform.gradle.plugin)
    implementation(libs.compose.gradle.get())
    implementation(libs.ktlint.gradle)
    implementation(libs.kover.gradle)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatformCompose") {
            id = libs.plugins.compose.convention.get().pluginId
            implementationClass = "ComposeConvention"
        }

        register("kotlinMultiplatformConvention") {
            id = libs.plugins.multiplatform.convention.get().pluginId
            implementationClass = "MultiplatformConvention"
        }
    }
}
