package xyz.luko.learning.session.model

import androidx.compose.ui.geometry.Offset
import xyz.luko.domain.model.Stroke

data class DrawingPageState(
    val referenceStrokes: List<Stroke> = emptyList(),
    val referenceHint: Stroke? = null,
    val userPreviousOffsets: List<List<Offset>> = emptyList(),
) {
    val isComplete: Boolean
        get() = referenceStrokes.size == userPreviousOffsets.size
}
