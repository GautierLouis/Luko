package xyz.luko.learning.session.model

import xyz.luko.domain.model.Stroke

data class DrawingPageState(
    val totalStrokes: Int = 0,
    val referenceStrokes: List<Stroke> = emptyList(),
    val referenceHint: Stroke? = null,
    val userPreviousOffsets: List<Stroke> = emptyList(),
) {
    val isComplete: Boolean
        get() = totalStrokes > 0 && userPreviousOffsets.size == totalStrokes
}
