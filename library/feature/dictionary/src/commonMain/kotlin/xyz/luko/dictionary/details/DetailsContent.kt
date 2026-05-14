package xyz.luko.dictionary.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.sp
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.DictionaryWithGraphic
import xyz.luko.domain.model.Session
import xyz.luko.domain.previewDictionaryWithGraphic
import xyz.luko.domain.previewSession

@Composable
internal fun DetailsContent(
    dictionaryWithGraphic: DictionaryWithGraphic,
    lastSession: List<Session> = emptyList(),
    onPractice: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Padding.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Spacing.large,
    ) {
        Text(
            text =
                dictionaryWithGraphic
                    .dictionary
                    .pinyin
                    .firstOrNull()
                    .orEmpty(),
            style = Theme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        AnimatedGraphic(dictionaryWithGraphic.graphics)

        Spacer(Modifier.height(32.dp))

        AppButton(
            text = Theme.strings.practice,
            size = ButtonSize.Large,
            onClick = onPractice,
        )

        if (lastSession.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Last Seen in:",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier =
                    Modifier
                        .fillMaxWidth(),
                textAlign = TextAlign.Start,
            )

            lastSession.forEach {
                // SessionCard(session = it)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDetailsContent(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        DetailsContent(
            dictionaryWithGraphic = previewDictionaryWithGraphic,
            lastSession = listOf(previewSession, previewSession),
        )
    }
}
