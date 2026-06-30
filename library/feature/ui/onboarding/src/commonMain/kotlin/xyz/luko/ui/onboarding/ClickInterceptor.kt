package xyz.luko.ui.onboarding

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import xyz.luko.ui.designsystem.onboarding.CutoutArea
import xyz.luko.ui.designsystem.onboarding.TooltipData

fun Modifier.clickInterceptor(current: TooltipData) = this.pointerInput(current) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent(PointerEventPass.Initial)
            event.changes.forEach { change ->
                if (!current.cutoutArea.contains(change.position)) {
                    change.consume()
                }
            }
        }
    }
}

internal fun CutoutArea.contains(offset: Offset): Boolean = when (this) {
    is CutoutArea.Circle -> (offset - center).getDistance() <= radius
    is CutoutArea.Rounded -> rect.contains(offset)
    is CutoutArea.Rectangular -> rect.contains(offset)
}
