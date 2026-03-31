package com.louisgautier.dictionary.home

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.components.button.v2.AppButtonV2
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonSize
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.dictionary.details.AnimatedGraphic
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.domain.previewSession
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun DetailsContent(
    dictionaryWithGraphic: DictionaryWithGraphic,
    lastSession: List<Session> = emptyList(),
    onPractice: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(Padding.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Spacing.large
    ) {
        Text(
            text = dictionaryWithGraphic
                .dictionary
                .pinyin
                .firstOrNull()
                .orEmpty(),
            style = Theme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AnimatedGraphic(dictionaryWithGraphic.graphics)

        Spacer(Modifier.height(32.dp))

        AppButtonV2(
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
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            lastSession.forEach {
                //SessionCard(session = it)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDetailsContent(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        DetailsContent(
            dictionaryWithGraphic = previewDictionaryWithGraphic,
            lastSession = listOf(previewSession, previewSession)
        )
    }
}