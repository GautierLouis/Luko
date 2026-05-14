import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
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

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
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
