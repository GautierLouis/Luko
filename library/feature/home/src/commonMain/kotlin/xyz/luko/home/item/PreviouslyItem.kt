package xyz.luko.home.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.baseui.session.toUiModel
import xyz.luko.domain.model.Session
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.components.metrics.SessionCard
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@Composable
internal fun PreviouslyItem(
    sessions: List<Session>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        HomeSectionItemTitle(
            title = "Previous Sessions",
            modifier = Modifier.padding(horizontal = 8.dp),
            action = {
                TextButton(
                    onClick = {
                        AppNavigation.navigate(AppRoute.Sessions.List())
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Theme.materialColors.background,
                    )
                ) {
                    Text(
                        text = "View all",
                        color = Theme.materialColors.onBackground,
                        style = Theme.typography.labelSmall,
                    )
                }
            }
        )

        HomeHorizonalPager(sessions) {
            SessionCard(
                model = sessions[it].toUiModel(),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    AppNavigation.navigate(
                        AppRoute.Sessions.List(sessions[it].id)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPreviouslyItem(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        PreviouslyItem(PreviewProvider.sessionList.take(5))
    }
}
