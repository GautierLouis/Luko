package xyz.luko.desktoApp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.DesktopComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runDesktopComposeUiTest
import androidx.compose.ui.unit.IntSize
import org.jetbrains.skia.Image
import org.junit.Test
import org.koin.compose.KoinApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.luko.app.Environment
import xyz.luko.app.app.App
import xyz.luko.app.libraryModule
import xyz.luko.domain.repository.AuthRepository
import xyz.luko.domain.repository.DictionaryRepository
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.TestWindowSize
import java.io.File
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalTestApi::class)
class AppScreenshotTest {

    private var currentSize by mutableStateOf(TestWindowSize.entries.first())

    private val modules = listOf(
        libraryModule,
        module {
            single(named(Environment.FLAVOR)) { "dev" }
            single(named(Environment.PLATFORM)) { "desktop" }
            single(named(Environment.VERSION_NAME)) { "1.0.0" }
            single(named(Environment.VERSION_CODE)) { "1" }
            single<AuthRepository> { FakeAuthRepository() }
            single<DictionaryRepository> { FakeDictionaryRepository() }
            single<SessionRepository> { FakeSessionRepo() }
        },
    )

    @Test
    fun `screenshot all screens with all sizes`() = runDesktopComposeUiTest {

        setContent {
            CompositionLocalProvider(
                LocalWindowInfo provides object : WindowInfo {
                    override val isWindowFocused = true
                    override val containerSize
                        get() = IntSize(
                            width = currentSize.width.value.toInt(),
                            height = currentSize.height.value.toInt()
                        )
                }
            ) {
                KoinApplication(application = {
                    allowOverride(true)
                    modules(modules)
                }) {
                    App()
                }
            }
        }

        screenshot(TestTags.Screen.HOME)

        onNodeWithTag(TestTags.Action.PRIMARY).performClick()

        waitScreen(TestTags.Screen.SESSION_BUILDER)
        screenshot(TestTags.Screen.SESSION_BUILDER, "0")
        onNodeWithTag(TestTags.Action.PRIMARY).performClick()
        screenshot(TestTags.Screen.SESSION_BUILDER, "1")
        onNodeWithTag(TestTags.Action.PRIMARY).performClick()
        screenshot(TestTags.Screen.SESSION_BUILDER, "2")

        waitScreen(TestTags.Screen.SESSION)
        screenshot(TestTags.Screen.SESSION)

    }

    private fun ComposeUiTest.waitScreen(name: String) {
        waitUntil(timeoutMillis = 3.seconds.inWholeMilliseconds) {
            onNodeWithTag(name).isDisplayed()
        }
    }

    @OptIn(InternalComposeUiApi::class)
    fun ComposeUiTest.screenshot(
        screenName: String,
        subDir: String? = null
    ) {
        val path = buildString {
            append("screenshots/")
            append(screenName)
            subDir?.let { append("/$it/") }
        }
        val screenshotDir = File(path).also { it.mkdirs() }

        val scene = (this as DesktopComposeUiTest).scene

        TestWindowSize.entries.forEach { size ->
            currentSize = size
            scene.size = IntSize(
                width = size.width.value.toInt(),
                height = size.height.value.toInt()
            )
            waitForIdle()

            val imageBitmap = ImageBitmap(size.width.value.toInt(), size.height.value.toInt())
            val composeCanvas = Canvas(imageBitmap)

            scene.render(composeCanvas, System.nanoTime())

            val bytes = Image.makeFromBitmap(imageBitmap.asSkiaBitmap()).encodeToData()!!.bytes
            File(screenshotDir, "${size.name}.png").writeBytes(bytes)
        }
    }
}
