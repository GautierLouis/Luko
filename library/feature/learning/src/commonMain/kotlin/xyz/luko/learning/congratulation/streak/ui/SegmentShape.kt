package xyz.luko.learning.congratulation.streak.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp

internal fun StreakDay.shape(
    progress: Float
): RoundedCornerShape {

    val radius = 50.dp
    val lerpRight = lerp(radius, 0.dp, progress)
    val lerpLeft = lerp(0.dp, radius, progress)

    return when (this.segment) {

        SegmentPosition.None ->
            androidx.compose.foundation.shape.RoundedCornerShape(radius)

        SegmentPosition.Single ->
            androidx.compose.foundation.shape.RoundedCornerShape(radius)

        SegmentPosition.Middle ->
            androidx.compose.foundation.shape.RoundedCornerShape(0.dp)

        SegmentPosition.First ->
            RoundedCornerShape(
                topStart = radius,
                bottomStart = radius,
                topEnd = if (isSunday) lerpRight else 0.dp,
                bottomEnd = if (isSunday) lerpRight else 0.dp
            )

        SegmentPosition.Last ->
            RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = if (isMonday) lerpLeft else radius,
                bottomEnd = if (isMonday) lerpLeft else radius
            )
    }
}
