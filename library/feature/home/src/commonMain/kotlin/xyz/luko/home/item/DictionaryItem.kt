package xyz.luko.home.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import xyz.luko.home.strings.learningStrings
import xyz.luko.ui.designsystem.icon.CharacterIcon
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.drawing.SvgGraphic
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@Composable
internal fun DictionaryCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.tertiaryContainer,
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            AppNavigation.navigate(AppRoute.Learning.BuildSession)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                SvgGraphic(
                    strokes = CharacterIcon.CI.stroke,
                    color = Theme.materialColors.inversePrimary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
                SvgGraphic(
                    strokes = CharacterIcon.DIAN.stroke,
                    color = Theme.materialColors.inversePrimary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }
            Text(
                text = Theme.learningStrings.cardDictionary,
                style = Theme.typography.titleMedium,
                color = Theme.materialColors.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(BiasAlignment(-0.5f, 0f))
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDictionaryCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        DictionaryCard(Modifier.height(150.dp))
    }
}
