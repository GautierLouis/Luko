package com.louisgautier.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.AppSize
import com.louisgautier.designsystem.token.string.StringsLocale
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Deprecated("Use V2 buttons instead")
@Composable
fun ElegantButton(
    modifier: Modifier = Modifier,
    variant: ButtonVariant,
    type: ButtonType,
    enabled: Boolean = true,
    onClick: () -> Unit,
    text: @Composable () -> Unit
) {

    val family = type.getColorFamily()

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(AppSize.size4),
        onClick = { onClick() },
        enabled = enabled,
        border = BorderStroke(1.dp, variant.borderColor(family)),
        colors = ButtonDefaults.buttonColors(
            contentColor = variant.contentColor(family),
            containerColor = variant.containerColor(family),
            disabledContentColor = variant.disableContentColor(family),
            disabledContainerColor = variant.disableContainerColor(family)
        )
    ) {
        text()
    }
}

@Preview(widthDp = 500)
@Composable
private fun PreviewButton(
    @PreviewParameter(ThemeModeProvider::class) mode: ThemeMode
) {
    AppThemeWrapper(
        themeMode = mode,
        locale = StringsLocale.FR
    ) {
        LazyColumn {
            items(ButtonType.entries.size) {
                val type = ButtonType.entries[it]
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonVariant.entries.forEach { variant ->
                        Box {
                            Test_PreviewButton(type, variant, true)
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonVariant.entries.forEach { variant ->
                        Box {
                            Test_PreviewButton(type, variant, false)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Test_PreviewButton(
    type: ButtonType = ButtonType.PRIMARY,
    variant: ButtonVariant = ButtonVariant.SOLID,
    enabled: Boolean = true
) {
    ElegantButton(
        variant = variant,
        type = type,
        enabled = enabled,
        onClick = { },
    ) {
        Text(text = Theme.strings.greeting)
    }
}