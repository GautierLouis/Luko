package com.louisgautier.designsystem.components.button.attrs

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

        ButtonRole.Error -> ButtonColors(
            containerColor = Theme.materialColors.errorContainer,
            contentColor = Theme.materialColors.error,
            borderColor = null,
        )
    }
    ButtonShape.Outlined -> ButtonColors(
        containerColor = Color.Transparent,
        contentColor = role.resolve(),
        borderColor = role.resolve(),
    )

    ButtonShape.Ghost -> ButtonColors(
        containerColor = Color.Transparent,
        contentColor = role.resolve(),
        borderColor = null,
    )
}

@Composable
private fun ButtonRole.resolve() = when (this) {
    ButtonRole.Primary -> Theme.materialColors.primary
    ButtonRole.Secondary -> Theme.materialColors.tertiary
    ButtonRole.Error -> Theme.materialColors.error
}