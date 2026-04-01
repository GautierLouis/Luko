package com.louisgautier.dictionary.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.topbar.action.ActionFilter
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.dictionary.home.ActiveFilter
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DictionaryTopBar(
    textFieldState: TextFieldState,
    activeFilter: ActiveFilter,
    modifier: Modifier = Modifier,
    filterMenuExpended: Boolean = false,
    enabled: Boolean = true,
    onEvent: (DictionaryScreenEvent) -> Unit = {}
) {

    val containerColor = if (filterMenuExpended) Theme.materialColors.surfaceContainer
    else Theme.materialColors.surface

    Column(
        modifier = Modifier
            .background(containerColor)
    ) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = Theme.strings.dictionary,
                    style = Theme.typography.titleLarge
                )
            },
            actions = {
                ActionFilter(enabled = enabled) { onEvent(DictionaryScreenEvent.OnFilterToggle) }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Theme.materialColors.onBackground,
                actionIconContentColor = Theme.materialColors.onBackground
            )
        )

        Column {
            DictionarySearchBar(textFieldState, enabled)

            AnimatedVisibility(
                visible = filterMenuExpended
            ) {
                HorizontalDivider(thickness = .5.dp, color = Theme.materialColors.outline)
                DictionaryFilter(activeFilter, onEvent)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDictionaryTopBar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column {
            DictionaryTopBar(
                textFieldState = rememberTextFieldState(),
                filterMenuExpended = false,
                enabled = true,
                activeFilter = ActiveFilter()
            )

            Spacer(Modifier.height(50.dp))

            DictionaryTopBar(
                textFieldState = rememberTextFieldState(),
                filterMenuExpended = true,
                enabled = true,
                activeFilter = ActiveFilter()
            )

            Spacer(Modifier.height(50.dp))

            DictionaryTopBar(
                textFieldState = rememberTextFieldState(),
                filterMenuExpended = false,
                enabled = false,
                activeFilter = ActiveFilter()
            )
        }
    }
}
