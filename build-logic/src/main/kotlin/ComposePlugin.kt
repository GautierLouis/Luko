import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


@Suppress("unused")
class ComposePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = extensions.getByType<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.compose.multiplatform.get().pluginId)
            apply(libs.plugins.compose.compiler.get().pluginId)
            apply(libs.plugins.compose.hot.reload.get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            configureComposeMultiplatform(libs)
        }
    }

    private fun KotlinMultiplatformExtension.configureComposeMultiplatform(libs: LibrariesForLibs) {

        val composeExt = project.extensions.findByType(ComposeExtension::class.java)
            ?: error("Compose extension not found — make sure 'org.jetbrains.compose' plugin is applied before configuring.")

        val compose = composeExt.dependencies

        sourceSets.apply {
            commonMain.dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.materialIconsExtended)
                implementation(compose.material3AdaptiveNavigationSuite)

                implementation(libs.compose.lifecycle.viewmodel.compose)
                implementation(libs.compose.lifecycle.runtime.compose)
                implementation(libs.compose.navigation.compose)
                implementation(libs.compose.backhandler)

                implementation(libs.kotlinx.collection.immutable)

                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.koin.compose.viewmodel.navigation)
            }

            commonTest.dependencies {
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
            }

            androidMain.dependencies {
                implementation(libs.androidx.activity.compose)
            }

            jvmMain.dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}