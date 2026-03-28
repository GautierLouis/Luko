package com.louisgautier.designsystem.components.button

import androidx.compose.runtime.Composable
import com.louisgautier.utils.CoreKeepForR8
import com.louisgautier.designsystem.theme.Theme

@CoreKeepForR8
enum class ButtonType {
    PRIMARY,
    SECONDARY,
    ERROR,
    WARNING,
    PREMIUM;

    @Composable
    fun getColorFamily() = when (this) {
        PRIMARY -> Theme.colors.grayFamily
        SECONDARY -> Theme.colors.tealFamily
        ERROR -> Theme.colors.redFamily
        WARNING -> Theme.colors.orangeFamily
        PREMIUM -> Theme.colors.yellowFamily
    }
}