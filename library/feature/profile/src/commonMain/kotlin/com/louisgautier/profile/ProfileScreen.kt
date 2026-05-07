package com.louisgautier.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.components.topbar.AppTopbar
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.domain.repository.SettingTheme
import kotlinx.collections.immutable.toPersistentList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen() {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ProfileScreen(
    state: ProfileViewModel.UiState,
    onEvent: (ProfileScreenEvent) -> Unit = {}
) {
    BaseScaffold(
        topBar = {
            ProfileTopbar()
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                horizontal = Padding.large
            )
        ) {

            item(key = ProfileSection.Theme) {
                SettingPicker(
                    selected = state.selectedTheme ?: SettingTheme.Default,
                    section = SettingSection.Theme(
                        items = SettingTheme.entries.toPersistentList(),
                    ),
                    onClick = {
                        onEvent(ProfileScreenEvent.OnThemeChanged(it))
                    }
                )
            }
        }
    }
}

@Composable
internal fun ProfileTopbar() {
    AppTopbar(title = Theme.strings.profile)
}

@Preview
@Composable
private fun PreviewProfileScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        ProfileScreen(
            state = ProfileViewModel.UiState()
        )
    }
}