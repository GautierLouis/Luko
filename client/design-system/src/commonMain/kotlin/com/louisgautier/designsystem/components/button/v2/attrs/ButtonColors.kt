package com.louisgautier.designsystem.components.button.v2.attrs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.louisgautier.designsystem.theme.Theme

internal data class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color?,
)

@Composable
internal fun resolveButtonStyle(
    shape: ButtonShape,
    role: ButtonRole,
): ButtonColors = when (shape) {
    ButtonShape.Filled -> when (role) {
        ButtonRole.Primary -> ButtonColors(
            containerColor = Theme.materialColors.primary,
            contentColor = Theme.materialColors.onPrimary,
            borderColor = null,
        )
        ButtonRole.Secondary -> ButtonColors(
            containerColor = Theme.materialColors.tertiary,
            contentColor = Theme.materialColors.onTertiary,
            borderColor = null,
        )
    }
    ButtonShape.Outlined -> when (role) {
        ButtonRole.Primary -> ButtonColors(
            containerColor = Theme.materialColors.background,
            contentColor = Theme.materialColors.onBackground,
            borderColor = Theme.materialColors.primary,
        )
        ButtonRole.Secondary -> ButtonColors(
            containerColor = Theme.materialColors.background,
            contentColor = Theme.materialColors.onBackground,
            borderColor = Theme.materialColors.tertiary,
        )
    }
}