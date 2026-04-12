package com.louisgautier.designsystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.button.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.attrs.ButtonSize
import com.louisgautier.designsystem.components.button.attrs.resolveButtonStyle
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing

@Composable
fun AppButton(
    text: String,
    shape: ButtonShape = ButtonShape.Filled,
    role: ButtonRole = ButtonRole.Primary,
    size: ButtonSize = ButtonSize.Small,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit = {},
) {

    val style = resolveButtonStyle(shape, role)

    val shape = when (shape) {
        ButtonShape.Filled -> ShapeDefaults.button()
        ButtonShape.Outlined -> ShapeDefaults.roundButton()
        ButtonShape.Ghost -> ShapeDefaults.roundButton()
    }

    val height = when (size) {
        ButtonSize.Small -> 34.dp
        ButtonSize.Medium -> 40.dp
        ButtonSize.Large -> 56.dp
    }

    val width = when (size) {
        ButtonSize.Small -> Modifier.wrapContentSize()
        ButtonSize.Medium -> Modifier.wrapContentSize()
        ButtonSize.Large -> Modifier.fillMaxWidth()
    }

    Button(
        modifier = modifier
            .height(height)
            .then(width),
        onClick = { onClick() },
        border = style.borderColor?.let { BorderStrokeDefaults.minimum(it) },
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = style.containerColor,
            contentColor = style.contentColor
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Spacing.medium
        ) {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
            Text(
                text = text,
                style = Theme.typography.labelLarge
            )
            trailingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    }
}


@Preview
@Composable
private fun PreviewAppButton(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AppButton(
                text = "Filled Primary",
                shape = ButtonShape.Filled,
                role = ButtonRole.Primary
            )
            AppButton(
                text = "Filled Secondary",
                shape = ButtonShape.Filled,
                role = ButtonRole.Secondary
            )
            AppButton(
                text = "Filled Error",
                shape = ButtonShape.Filled,
                role = ButtonRole.Error
            )
            AppButton(
                text = "Outline Primary",
                shape = ButtonShape.Outlined,
                role = ButtonRole.Primary
            )
            AppButton(
                text = "Outline Secondary",
                shape = ButtonShape.Outlined,
                role = ButtonRole.Secondary
            )
            AppButton(
                text = "Outline Error",
                shape = ButtonShape.Outlined,
                role = ButtonRole.Secondary
            )
            AppButton(
                text = "Ghost Primary",
                shape = ButtonShape.Ghost,
                role = ButtonRole.Primary
            )
            AppButton(
                text = "Ghost Secondary",
                shape = ButtonShape.Ghost,
                role = ButtonRole.Secondary
            )
            AppButton(
                text = "Ghost Error",
                shape = ButtonShape.Ghost,
                role = ButtonRole.Error
            )
        }
    }
}