import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

@Suppress("unused")
class MultiplatformConvention : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = extensions.getByType<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.kotlin.multiplatform.get().pluginId)
            apply(libs.plugins.android.kotlin.multiplatform.library.get().pluginId)
            apply(libs.plugins.serialization.get().pluginId)
            apply(libs.plugins.ktlint.get().pluginId)
            apply(libs.plugins.kover.get().pluginId)
        }

        configureKtLint(libs)
        configureKotlinMultiplatform()
        configureAndroid(libs)
        configureBaseSourceSets(libs)
    }

    private fun Project.configureKtLint(libs: LibrariesForLibs) {
        extensions.configure<KtlintExtension> {
            version.set(libs.versions.ktlint.engine)
            verbose.set(true)
            outputToConsole.set(true)
            coloredOutput.set(true)
        }

        dependencies {
            add(
                "ktlintRuleset",
                libs.ktlint.ruleset.compose.get()
            )
        }
    }

    private fun Project.configureKotlinMultiplatform() {
        extensions.configure<KotlinMultiplatformExtension> {
            // JVM target with toolchain
            jvmToolchain(17)
            jvm()

            // iOS targets
            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            ).forEach { iosTarget ->
                sourceSets.findByName(iosTarget.name)
                    ?.kotlin?.srcDir("build/generated/ksp/$iosTarget/kotlin")
            }

            applyDefaultHierarchyTemplate()
        }
    }

    private fun Project.configureAndroid(libs: LibrariesForLibs) {
        pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
            extensions.configure<KotlinMultiplatformExtension> {
                targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                    namespace = "xyz.luko.${project.name.replace(":", ".").trim('.')}"
                    compileSdk = libs.versions.android.target.sdk.get().toInt()
                    minSdk = libs.versions.android.min.sdk.get().toInt()
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
            }
        }
    }

    private fun Project.configureBaseSourceSets(libs: LibrariesForLibs) {
        extensions.configure<KotlinMultiplatformExtension> {
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
                    implementation(libs.kotlinx.coroutines.test)
                }
            }
        }
    }
}

