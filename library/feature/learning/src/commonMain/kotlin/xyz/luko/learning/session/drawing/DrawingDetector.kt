package xyz.luko.learning.session.drawing

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

@Composable
internal fun Modifier.drawingDetector(
    points: SnapshotStateList<Offset>,
    onGestureComplete: () -> Unit = {}
): Modifier {
    val currentOnGestureComplete by rememberUpdatedState(onGestureComplete)
    return this.pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { offset ->
                points.clear()
                points.add(offset)
            },
            onDrag = { change, dragAmount ->
                points.add(change.position)
                change.consume()
            },
            onDragEnd = {
                currentOnGestureComplete()
                points.clear()
            },
            onDragCancel = {
                currentOnGestureComplete()
                points.clear()
            }
        )
    }.clipToBounds()
}