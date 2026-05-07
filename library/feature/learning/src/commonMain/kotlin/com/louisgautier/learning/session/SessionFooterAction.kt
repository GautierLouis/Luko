package com.louisgautier.learning.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.designsystem.components.button.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.attrs.ButtonSize
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing

@Composable
internal fun FooterAction(
    isLastQuestion: Boolean,
    isAnswered: Boolean,
    modifier: Modifier = Modifier,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(Padding.large),
        verticalArrangement = Spacing.medium
    ) {
        AppButton(
            text = if (isLastQuestion) Theme.strings.sessionComplete else Theme.strings.sessionFinish,
            shape = ButtonShape.Filled,
            role = ButtonRole.Primary,
            enabled = isAnswered,
            size = ButtonSize.Large,
            onClick = {
                val event =
                    if (isLastQuestion) SessionScreenEvent.Next else SessionScreenEvent.Finish
                onEvent(event)
            },
        )

        AppButton(
            text = Theme.strings.sessionQuit,
            shape = ButtonShape.Ghost,
            role = ButtonRole.Error,
            size = ButtonSize.Large,
            onClick = { onEvent(SessionScreenEvent.ToggleLeaveDialog) },
        )
    }
}

@Preview
@Composable
private fun PreviewSessionFooterAction(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        FooterAction(isLastQuestion = true, isAnswered = true)
    }
}