package com.louisgautier.designsystem.components.button.v2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonSize
import com.louisgautier.designsystem.components.button.v2.attrs.resolveButtonStyle
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun AppButtonV2(
    text: String,
    shape: ButtonShape = ButtonShape.Filled,
    role: ButtonRole = ButtonRole.Primary,
    size: ButtonSize = ButtonSize.Medium,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit = {},
) {

    val style = resolveButtonStyle(shape, role)

    val shape = when (shape) {
        ButtonShape.Filled -> ShapeDefaults.button()
        ButtonShape.Outlined -> ShapeDefaults.roundButton()
    }

    val height = when (size) {
        ButtonSize.Small -> 32.dp
        ButtonSize.Medium -> 40.dp
        ButtonSize.Large -> 56.dp
    }

    Button(
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        onClick = { onClick() },
        border = style.borderColor?.let { BorderStrokeDefaults.minimum(it) },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = style.containerColor,
            contentColor = style.contentColor
        )
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


@Preview
@Composable
private fun PreviewAppButtonV2(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AppButtonV2(
                text = "Filled Primary",
                shape = ButtonShape.Filled,
                role = ButtonRole.Primary
            )
            AppButtonV2(
                text = "Filled Secondary",
                shape = ButtonShape.Filled,
                role = ButtonRole.Secondary
            )
            AppButtonV2(
                text = "Outline Primary",
                shape = ButtonShape.Outlined,
                role = ButtonRole.Primary
            )
            AppButtonV2(
                text = "Outline Secondary",
                shape = ButtonShape.Outlined,
                role = ButtonRole.Secondary
            )
        }
    }
}