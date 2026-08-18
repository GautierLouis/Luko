package xyz.luko.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.baseui.session.colorFamily
import xyz.luko.baseui.session.label
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.PlayArrow
import xyz.luko.ui.designsystem.icon.Refresh
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

/**
 * @param lastSessionSettings size is >=1 and <=2
 */
@Composable
internal fun AgainItem(lastSessionSettings: List<LastSessionSettings>) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HomeSectionItemTitle(title = "Do again")
        lastSessionSettings.forEach { lastSessionSettings ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Theme.materialColors.surfaceContainer,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .border(
                        border = BorderStroke(1.dp, Theme.materialColors.outline),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            Theme.materialColors.outlineVariant,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = AppIcon.Refresh,
                        contentDescription = null,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = lastSessionSettings.difficultyLevel.colorFamily().subtle,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Text(
                                text = lastSessionSettings.difficultyLevel.label().uppercase(),
                                style = Theme.typography.labelSmall,
                                color = lastSessionSettings.difficultyLevel.colorFamily().primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = Theme.materialColors.outlineVariant,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Text("10 QS", style = Theme.typography.labelSmall)
                        }
                    }

                    Text(
                        text = buildString {
                            val cf = lastSessionSettings.frequencyLevel
                                .map { it.name.lowercase().capitalize(Locale.current) }
                                .joinWithAnd()
                            append(cf)
                            append(" ")
                            append("Characters")
                        },
                        style = Theme.typography.bodyMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            Theme.materialColors.outlineVariant,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = AppIcon.PlayArrow,
                        contentDescription = null,
                    )
                }
            }
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
private fun PreviewAgainItem(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        AgainItem(
            listOf(
                LastSessionSettings(
                    difficultyLevel = DifficultyLevel.HARD,
                    count = 5,
                    frequencyLevel = listOf(CharacterFrequencyLevel.COMMON)
                ),
                LastSessionSettings(
                    difficultyLevel = DifficultyLevel.HARD,
                    count = 5,
                    frequencyLevel = listOf(CharacterFrequencyLevel.COMMON)
                )
            )
        )
    }
}
