package xyz.luko.baseui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
//
// @OptIn(ExperimentalTestApi::class)
// class MainTest {
//
//    @BeforeTest
//    fun setup() {
//        startKoin { modules(libraryModule) }
//    }
//
//    @AfterTest
//    fun teardown() = stopKoin()
//
//    @Test
//    fun allSizes_noComponentCropped() {
//        TestWindowSize.entries.forEach { size ->
//            runComposeUiTest {
//                setContent {
//                    Box(modifier = Modifier.size(size.width, size.height)) {
//                        App()
//                    }
//                }
//                assertNothingIsCropped()
//            }
//        }
//    }
// }
//
// @OptIn(ExperimentalTestApi::class)
// fun runDesktopTest(
//    size: TestWindowSize,
//    block: ComposeUiTest.() -> Unit
// ) = runComposeUiTest {
//    setContent {
//        Box(modifier = Modifier.size(size.width, size.height)) {
//            block()
//        }
//    }
// }
//
// @OptIn(ExperimentalTestApi::class)
// fun ComposeUiTest.assertNothingIsCropped() {
//    val rootBounds = onRoot().fetchSemanticsNode().boundsInRoot
//
//    onAllNodes(hasTestTag(""), useUnmergedTree = true)
//        .fetchSemanticsNodes()
//        .filter { node ->
//            node.config.contains(SemanticsProperties.TestTag)
//        }
//        .forEach { node ->
//            val tag = node.config[SemanticsProperties.TestTag]
//            val bounds = node.boundsInRoot
//
//            assert(
//                bounds.left >= rootBounds.left - 1f &&
//                        bounds.top >= rootBounds.top - 1f &&
//                        bounds.right <= rootBounds.right + 1f &&
//                        bounds.bottom <= rootBounds.bottom + 1f
//            ) {
//                "Node '$tag' is cropped.\n" +
//                        "Node bounds:  left=${bounds.left}, top=${bounds.top}, right=${bounds.right}, bottom=${bounds.bottom}\n" +
//                        "Root bounds:  left=${rootBounds.left}, top=${rootBounds.top}, right=${rootBounds.right}, bottom=${rootBounds.bottom}"
//            }
//        }
// }
