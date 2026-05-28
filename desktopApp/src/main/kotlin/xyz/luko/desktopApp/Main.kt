package xyz.luko.desktopApp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import xyz.luko.app.app.App
import xyz.luko.app.libraryModule
import java.io.File

fun main() {

    val enableCycling = System.getProperty("enableCycling") == "true"

    startKoin {
        modules(libraryModule)
    }

    application {
        val sizes = TestWindowSize.entries
        LaunchedEffect(Unit) {
            if (enableCycling) {
                for (index in sizes.indices) {
                    takeScreenshot2(sizes[index])
                }
                exitApplication()
            }
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Luko",
        ) {
            App()
        }
    }

}

private fun takeScreenshot2(size: TestWindowSize) {
    val screenshotDir = File("screenshots/manual").also { it.mkdirs() }
    val filename = "${size.name}_${size.width.value.toInt()}x${size.height.value.toInt()}.png"

    val width = size.width.value.toInt()
    val height = size.height.value.toInt()

    val scene = ImageComposeScene(
        width = width,
        height = height,
        density = Density(1f)
    ) {
        App()
    }

    val image = scene.render()
    val bytes = image.encodeToData()!!.bytes

    File(screenshotDir, filename).writeBytes(bytes)
    scene.close()
}

private enum class TestWindowSize(
    val width: Dp,
    val height: Dp,
) {
    // Android phones portrait
    SMALL_PHONE_PORTRAIT(360.dp, 640.dp),
    MEDIUM_PHONE_PORTRAIT(390.dp, 844.dp),
    LARGE_PHONE_PORTRAIT(430.dp, 932.dp),

    // Android phones landscape
    SMALL_PHONE_LANDSCAPE(640.dp, 360.dp),
    MEDIUM_PHONE_LANDSCAPE(844.dp, 390.dp),
    LARGE_PHONE_LANDSCAPE(932.dp, 430.dp),

    // Android tablets
    TABLET_PORTRAIT(800.dp, 1280.dp),
    TABLET_LANDSCAPE(1280.dp, 800.dp),

    // iPhone
    IPHONE_SE_PORTRAIT(375.dp, 667.dp),
    IPHONE_15_PORTRAIT(393.dp, 852.dp),
    IPHONE_15_LANDSCAPE(852.dp, 393.dp),
    IPHONE_15_PRO_MAX_PORTRAIT(430.dp, 932.dp),
    IPHONE_15_PRO_MAX_LANDSCAPE(932.dp, 430.dp),

    DESKTOP(800.dp, 600.dp),
}
