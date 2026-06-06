package xyz.luko.ui.drawing.internal.model

import androidx.compose.ui.graphics.Path

data class TransformedHint(
    val strokePath: Path,
    val arrowHead: Path?,
)
