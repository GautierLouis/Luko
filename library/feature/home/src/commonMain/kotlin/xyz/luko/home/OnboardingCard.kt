package xyz.luko.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.ArrowBack
import xyz.luko.ui.designsystem.icon.CharacterIcon
import xyz.luko.ui.designsystem.icon.RoundedStar
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.drawing.SvgGraphic

@Composable
internal fun OnboardingCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                color = Theme.materialColors.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        SvgGraphic(
            strokes = CharacterIcon.XUE.stroke,
            color = Theme.materialColors.outlineVariant,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .aspectRatio(1f)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .background(
                        color = Theme.materialColors.background,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        border = BorderStroke(1.dp, Theme.materialColors.outline),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Icon(
                    AppIcon.RoundedStar,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "NEW ON APP",
                    style = Theme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.sp
                )
            }

            Text(
                text = "Getting Started Guide",
                style = Theme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )

            Text(
                text = "Learn how to make the most of the app, track your mastery, and build a consistent habit.",
                style = Theme.typography.bodySmall,
                color = Theme.materialColors.outline,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .padding(start = 16.dp)
            )

            TextButton(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.materialColors.surfaceContainer,
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            ) {
                Text(
                    text = "Start Guide",
                    color = Theme.materialColors.onSurface,
                    style = Theme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = AppIcon.ArrowBack,
                    contentDescription = null,
                    tint = Theme.materialColors.onSurface,
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .size(12.dp).rotate(180f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewOnboardingCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        OnboardingCard(
            modifier = Modifier.height(150.dp)
        )
    }
}
