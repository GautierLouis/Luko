package xyz.luko.desktopApp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import org.koin.core.context.startKoin
import xyz.luko.app.app.App
import xyz.luko.app.libraryModule
import java.awt.Toolkit
import kotlin.time.Duration.Companion.seconds

fun main() {

    val enableCycling = System.getProperty("enableCycling") == "true"

    startKoin {
        modules(libraryModule)
    }

    application {

        val sizes = TestWindowSize.entries
        var currentIndex by remember { mutableStateOf(0) }

        val windowState = rememberWindowState(
            width = sizes[0].width,
            height = sizes[0].height
        )

        LaunchedEffect(Unit) {
            while (enableCycling) {
                delay(3.seconds)
                currentIndex = (currentIndex + 1) % sizes.size
                val newSize = sizes[currentIndex]
                windowState.size = DpSize(newSize.width, newSize.height)

                // Clamp position so window stays on screen
                val screenSize = Toolkit.getDefaultToolkit().screenSize
                val maxX = (screenSize.width - newSize.width.value).coerceAtLeast(0f)
                val maxY = (screenSize.height - newSize.height.value).coerceAtLeast(0f)
                windowState.position = WindowPosition(
                    x = windowState.position.x.value.coerceIn(0f, maxX).dp,
                    y = windowState.position.y.value.coerceIn(0f, maxY).dp
                )
            }
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Luko",
            state = windowState
        ) {
            App()
        }
    }

}

enum class TestWindowSize(
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
