package com.louisgautier.learning.session.drawing

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.drawingDetector(
    points: SnapshotStateList<Offset>,
    onGestureComplete: () -> Unit = {}
): Modifier {
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
                onGestureComplete()
                points.clear()
            },
            onDragCancel = {
                onGestureComplete()
                points.clear()
            }
        )
    }.clipToBounds()
}