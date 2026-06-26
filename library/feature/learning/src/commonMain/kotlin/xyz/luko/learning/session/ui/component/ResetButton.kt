package xyz.luko.learning.session.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.Reset
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults

@Composable
internal fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(38.dp),
        colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = Theme.materialColors.tertiaryContainer,
                contentColor = Theme.materialColors.onTertiaryContainer,
            ),
        shape = ShapeDefaults.button(),
    ) {
        Icon(
            imageVector = AppIcon.Reset,
            contentDescription = Theme.strings.actionReset,
            modifier =
                Modifier
                    .padding(Padding.medium)
                    .graphicsLayer { scaleX = -1f },
        )
    }
}

@Preview
@Composable
private fun PreviewResetButton(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        ResetButton()
    }
}
