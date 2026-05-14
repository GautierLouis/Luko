package xyz.luko.designsystem.components.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.components.topbar.action.ActionContainer
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopbar(
    title: String,
    modifier: Modifier = Modifier,
    leftIcons: @Composable RowScope.() -> Unit = {},
    rightIcons: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, style = Theme.typography.titleLarge)
        },
        navigationIcon = {
            ActionContainer { leftIcons() }
        },
        actions = {
            ActionContainer { rightIcons() }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Theme.materialColors.background,
                navigationIconContentColor = Theme.materialColors.onBackground,
                titleContentColor = Theme.materialColors.onBackground,
                actionIconContentColor = Theme.materialColors.onBackground,
            ),
    )
}

@Preview
@Composable
private fun PreviewAppTopbar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        AppTopbar("Title")
    }
}
