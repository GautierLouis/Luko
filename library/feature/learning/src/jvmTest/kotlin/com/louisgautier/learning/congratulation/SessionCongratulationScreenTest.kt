package com.louisgautier.learning.congratulation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.louisgautier.baseui.TestSizes
import com.louisgautier.baseui.TestWindowSize
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.theme.AppTheme
import kotlin.test.Test

class SessionCongratulationScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun sessionScreen_allSizes_noComponentCropped() {
        TestSizes.ALL.forEach { size ->
            runDesktopTest(size) {
                setContent {
                    AppTheme(ThemeMode.Day) {
                        SessionCongratulationScreen()
                    }
                }
                assertNothingIsCropped()
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertNothingIsCropped() {
        val rootBounds = onRoot().fetchSemanticsNode().boundsInRoot

        onAllNodes(hasTestTag(""), useUnmergedTree = true)
            .fetchSemanticsNodes()
            .filter { node ->
                node.config.contains(SemanticsProperties.TestTag)
            }
            .forEach { node ->
                val tag = node.config[SemanticsProperties.TestTag]
                val bounds = node.boundsInRoot

                assert(
                    bounds.left >= rootBounds.left - 1f &&
                            bounds.top >= rootBounds.top - 1f &&
                            bounds.right <= rootBounds.right + 1f &&
                            bounds.bottom <= rootBounds.bottom + 1f
                ) {
                    "Node '$tag' is cropped.\n" +
                            "Node bounds:  left=${bounds.left}, top=${bounds.top}, right=${bounds.right}, bottom=${bounds.bottom}\n" +
                            "Root bounds:  left=${rootBounds.left}, top=${rootBounds.top}, right=${rootBounds.right}, bottom=${rootBounds.bottom}"
                }
            }
    }
}

@OptIn(ExperimentalTestApi::class)
fun runDesktopTest(
    size: TestWindowSize,
    block: ComposeUiTest.() -> Unit
) = runComposeUiTest {
    setContent {
        Box(modifier = Modifier.size(size.width, size.height)) {
            block()
        }
    }
}