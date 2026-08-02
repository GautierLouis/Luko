package xyz.luko.ui.drawing.internal

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import xyz.luko.domain.model.Point
import kotlin.time.Clock

@Composable
internal fun Modifier.drawingDetector(
    points: SnapshotStateList<Point.Straight>,
    onGestureComplete: () -> Unit = {},
): Modifier {
    val currentOnGestureComplete by rememberUpdatedState(onGestureComplete)
    return this
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset ->
                    points.clear()
                    points.add(
                        Point.Straight(
                            offset.x,
                            offset.y,
                            Clock.System.now().toEpochMilliseconds()
                        )
                    )
                },
                onDrag = { change, _ ->
                    points.add(
                        Point.Straight(
                            change.position.x,
                            change.position.y,
                            Clock.System.now().toEpochMilliseconds()
                        )
                    )
                    change.consume()
                },
                onDragEnd = {
                    currentOnGestureComplete()
                    points.clear()
                },
                onDragCancel = {
                    currentOnGestureComplete()
                    points.clear()
                },
            )
        }.clipToBounds()
}
