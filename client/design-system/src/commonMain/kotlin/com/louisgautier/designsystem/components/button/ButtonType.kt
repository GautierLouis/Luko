package com.louisgautier.designsystem.components.button

import androidx.compose.runtime.Composable
import com.louisgautier.utils.CoreKeepForR8
import com.louisgautier.designsystem.theme.AppTheme

@CoreKeepForR8
enum class ButtonType {
    PRIMARY,
    SECONDARY,
    ERROR,
    WARNING,
    PREMIUM;

    @Composable
    fun getColorFamily() = when (this) {
        PRIMARY -> AppTheme.colors.grayFamily
        SECONDARY -> AppTheme.colors.tealFamily
        ERROR -> AppTheme.colors.redFamily
        WARNING -> AppTheme.colors.orangeFamily
        PREMIUM -> AppTheme.colors.yellowFamily
    }
}