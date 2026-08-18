package xyz.luko.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.ui.designsystem.icon.CharacterIcon
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.drawing.SvgGraphic
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@Composable
internal fun LearnItem() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        HomeSectionItemTitle(title = "Learn")
        Card(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(120.dp),
            colors = CardDefaults.cardColors(
                containerColor = Theme.materialColors.primaryContainer,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                AppNavigation.navigate(AppRoute.LearningRoute.NewSessionRoute)
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                SvgGraphic(
                    strokes = CharacterIcon.HUA.stroke,
                    color = Theme.materialColors.inversePrimary,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
                Text(
                    text = "Practice Drawing",
                    style = Theme.typography.titleMedium,
                    color = Theme.materialColors.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(BiasAlignment(-0.5f, 0f))
                )
            }
        }
    }
}


@Preview
@Composable
private fun PreviewLearnItem(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        LearnItem()
    }
}
