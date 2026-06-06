plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(projects.library.app)
    implementation(libs.koin.compose)
    implementation(compose.desktop.currentOs)

    testImplementation(libs.koin.test)
    testImplementation(projects.library.feature.ui.core) //For testTags
    testImplementation(projects.library.domain) // For mock
    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.androidx.paging.compose)
    testImplementation(libs.compose.ui.test.junit4)

}

compose.desktop {
    application {
        mainClass = "xyz.luko.desktopApp.MainKt"

        jvmArgs += listOf("-DenableCycling=${project.hasProperty("enableCycling")}")

    }
}
