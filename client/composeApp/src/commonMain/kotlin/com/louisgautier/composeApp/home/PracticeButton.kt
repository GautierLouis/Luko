package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.domain.previewSession
import com.louisgautier.domain.previewStatistics
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun PracticeButton(
    modifier: Modifier = Modifier.Companion,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 12.dp,
            pressedElevation = 6.dp,
        ),
        modifier = modifier
            .padding(bottom = Padding.large)
            .size(144.dp),
        containerColor = Theme.materialColors.primary,
    ) {
        Text(
            text = Theme.strings.practice,
            style = Theme.typography.titleLarge,
            color = Theme.materialColors.onPrimary,
        )
    }
}

@Composable
@Preview
private fun PreviewPracticeButton(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        PracticeButton()
    }
}