package xyz.luko.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.PlayArrow
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

@Composable
internal fun PracticeButton(
    attrs: PracticeButtonAttrs,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val shape =
        when (attrs) {
            PracticeButtonAttrs.SMALL -> FloatingActionButtonDefaults.smallShape
            PracticeButtonAttrs.LARGE -> CircleShape
        }

    val size =
        when (attrs) {
            PracticeButtonAttrs.SMALL -> 56.dp
            PracticeButtonAttrs.LARGE -> 144.dp
        }

    FloatingActionButton(
        onClick = onClick,
        shape = shape,
        elevation =
            FloatingActionButtonDefaults.elevation(
                defaultElevation = 12.dp,
                pressedElevation = 6.dp,
            ),
        modifier = modifier.size(size),
        containerColor = Theme.materialColors.primary,
    ) {
        when (attrs) {
            PracticeButtonAttrs.LARGE -> {
                Text(
                    text = Theme.strings.practice,
                    style = Theme.typography.titleLarge,
                    color = Theme.materialColors.onPrimary,
                )
            }

            PracticeButtonAttrs.SMALL -> {
                Icon(
                    imageVector = AppIcon.PlayArrow,
                    tint = Theme.materialColors.onPrimary,
                    contentDescription = Theme.strings.practice,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPracticeButton(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        Column {
            PracticeButton(PracticeButtonAttrs.LARGE)
            PracticeButton(PracticeButtonAttrs.SMALL)
        }
    }
}
