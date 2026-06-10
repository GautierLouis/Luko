package xyz.luko.designsystem.token.color.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class LevelColors(
    val primary: Color,
    val onPrimary: Color,
    val subtle: Color,
    val onSubtle: Color,
)
