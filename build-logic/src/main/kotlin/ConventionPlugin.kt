import com.android.build.api.dsl.androidLibrary
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class ConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = extensions.getByType<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.android.kotlin.multiplatform.library.get().pluginId)
            apply(libs.plugins.kotlin.multiplatform.get().pluginId)
            apply(libs.plugins.serialization.get().pluginId)
            apply(libs.plugins.mokkery.plugin.get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidLibrary {
                namespace = "com.louisgautier.${target.name.replace(":", ".").trim('.')}"
                compileSdk = libs.versions.android.target.sdk.get().toInt()
                minSdk = libs.versions.android.min.sdk.get().toInt()
                compilations.configureEach {
                    compileTaskProvider.configure {
                        compilerOptions {
                            jvmTarget.set(JvmTarget.JVM_21)
                        }
                    }
                }
            }

            configureTargets()
            configureBaseSourceSets(libs)
        }
    }

    private fun KotlinMultiplatformExtension.configureTargets() {
        jvmToolchain(21)

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            sourceSets.findByName(iosTarget.name)
                ?.kotlin?.srcDir("build/generated/ksp/$iosTarget/kotlin")
        }

        jvm()

        applyDefaultHierarchyTemplate()
    }

    private fun KotlinMultiplatformExtension.configureBaseSourceSets(libs: LibrariesForLibs) {
        sourceSets.apply {
            commonMain.dependencies {
                implementation(libs.koin.core)

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }

            commonTest.dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.koin.test)
                implementation(libs.mokkery.coroutine)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}

