package xyz.luko.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toPersistentList
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.components.topbar.AppTopbar
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.domain.repository.SettingTheme

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