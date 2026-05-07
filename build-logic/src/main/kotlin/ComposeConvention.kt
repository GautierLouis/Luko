import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
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

        configureComposeMultiplatform(libs)
    }

    private fun Project.configureComposeMultiplatform(libs: LibrariesForLibs) {
        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain.dependencies {
                    implementation(libs.compose.runtime)
                    implementation(libs.compose.foundation)
                    implementation(libs.compose.material3)
                    implementation(libs.compose.material3.adaptive)
                    implementation(libs.compose.ui)
                    implementation(libs.compose.components.resources)
                    implementation(libs.compose.components.ui.tooling.preview)

                    implementation(libs.compose.lifecycle.viewmodelNavigation3)
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
                    implementation(libs.compose.ui.test)
                }

                androidMain.dependencies {
                    implementation(libs.androidx.compose.ui.tooling)
                }
            }

            compilerOptions {
                freeCompilerArgs.set(listOf("-Xcontext-parameters"))
            }
        }
    }
}