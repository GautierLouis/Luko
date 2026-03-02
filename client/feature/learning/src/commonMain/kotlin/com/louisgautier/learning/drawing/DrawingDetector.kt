package com.louisgautier.learning.drawing

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.drawingDetector(
    points: SnapshotStateList<Offset>,
    onEnd: () -> Unit = {}
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
                onEnd()
                points.clear()
            },
            onDragCancel = {
                onEnd()
                points.clear()
            }
        )
    }.clipToBounds()
}