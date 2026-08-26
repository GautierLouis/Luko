package xyz.luko.home.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.baseui.session.colorFamily
import xyz.luko.baseui.session.label
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.SessionSettings
import xyz.luko.home.strings.learningStrings
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.Refresh
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute


@Composable
internal fun ContinueSessionCard(
    sessionSettings: SessionSettings,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer,
        ),
        border = BorderStroke(1.dp, Theme.materialColors.primary),
        onClick = {
            AppNavigation.navigate(
                AppRoute.Learning.StartSession(sessionSettings), clearBackStack = true
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(8.dp),
        ) {
            Icon(
                imageVector = AppIcon.Refresh,
                contentDescription = null,
                tint = Theme.materialColors.outline.copy(alpha = .2f),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = sessionSettings.difficultyLevel.colorFamily().subtle,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = sessionSettings.difficultyLevel.label().uppercase(),
                    style = Theme.typography.labelSmall,
                    color = sessionSettings.difficultyLevel.colorFamily().primary,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(
                        color = Theme.appLevelColors.appMetrics.subtle,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = Theme.learningStrings.continueSessionQsCount(sessionSettings.count),
                    style = Theme.typography.labelSmall,
                    color = Theme.appLevelColors.appMetrics.primary,
                    fontWeight = FontWeight.Bold,
                )
            }

            Text(
                text = buildString {
                    val cf = sessionSettings.frequencyLevel
                        .map { it.name.lowercase().capitalize(Locale.current) }
                        .joinWithAnd()
                    append(cf)
                    append(" ")
                    append(Theme.learningStrings.character)
                },
                style = Theme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                color = Theme.materialColors.onSurface
            )
        }
    }
}

private fun List<String>.joinWithAnd(): String = when (size) {
    0 -> ""
    1 -> this[0]
    2 -> "${this[0]} & ${this[1]}"
    else -> dropLast(1).joinToString(", ") + ", & " + last()
}

@Preview
@Composable
private fun PreviewContinueSessionCard(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        ContinueSessionCard(
            SessionSettings(
                difficultyLevel = DifficultyLevel.HARD,
                count = 20,
                frequencyLevel = listOf(
                    CharacterFrequencyLevel.COMMON,
                    CharacterFrequencyLevel.FREQUENT,
                    CharacterFrequencyLevel.STANDARD
                )
            ),
            modifier = Modifier.size(150.dp)
        )
    }
}
