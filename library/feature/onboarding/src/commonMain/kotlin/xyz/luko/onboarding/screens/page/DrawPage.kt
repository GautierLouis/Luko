package xyz.luko.onboarding.screens.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.onboarding.strings.onboardingString
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults
import xyz.luko.ui.drawing.DrawableArea

@Composable
internal fun DrawPage() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(58.dp)
        ) {
            Text(
                text = Theme.onboardingString.drawTitle,
                style = Theme.typography.displayLarge,
                color = Theme.materialColors.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = Theme.onboardingString.drawCaption,
                style = Theme.typography.titleLarge,
                color = Theme.materialColors.onSecondaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            DrawableArea(
                enableDrawing = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .background(
                        color = Theme.materialColors.background,
                        shape = ShapeDefaults.card(),
                    ).border(
                        border = BorderStrokeDefaults.medium(Theme.materialColors.primary),
                        shape = ShapeDefaults.tag(),
                    )

                    .aspectRatio(1f),
                reference = PreviewProvider.dictionary.medians,
                userStroke = PreviewProvider.dictionary.medians.take(3),
                hint = PreviewProvider.dictionary.medians[3]
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDrawPage(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        DrawPage()
    }
}
