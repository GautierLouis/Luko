plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(projects.library.app)
    implementation(libs.koin.compose)
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "xyz.luko.desktopApp.MainKt"
    }
}