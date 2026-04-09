import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


@Suppress("unused")
class ComposeConvention : Plugin<Project> {

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

        composeExt.dependencies

        sourceSets.apply {
            commonMain.dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.components.ui.tooling.preview)

                implementation(libs.compose.lifecycle.viewmodel)
                implementation(libs.compose.lifecycle.runtime)
                implementation(libs.compose.navigation)
                implementation(libs.compose.ui.backhandler)

                implementation(libs.kotlinx.collection.immutable)

                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.koin.compose.viewmodel.navigation)
            }

            commonTest.dependencies {
                implementation(libs.compose.ui)
            }

            androidMain.dependencies {
                implementation(libs.androidx.compose.ui.tooling)
            }
        }
    }
}