package xyz.luko.onboarding.screens.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import xyz.luko.domain.model.Level
import xyz.luko.onboarding.strings.onboardingString
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProgressPage() {
    var currentStep by rememberSaveable { mutableIntStateOf(0) }

    val stepDuration = 900L
    val activeColor = Theme.materialColors.primary
    val inactiveColor = Theme.materialColors.outlineVariant
    val stepCount = Level.entries.size

    LaunchedEffect(stepCount) {
        while (true) {
            delay(stepDuration.milliseconds)
            currentStep = (currentStep + 1) % stepCount
        }
    }

    val animatedValue by animateFloatAsState(
        targetValue = currentStep.toFloat(),
        animationSpec = tween(durationMillis = 300),
        label = "slider-value"
    )

    val colors = SliderDefaults.colors(
        disabledThumbColor = activeColor,
        disabledActiveTrackColor = activeColor,
        disabledInactiveTrackColor = inactiveColor,
        disabledActiveTickColor = activeColor,
        disabledInactiveTickColor = inactiveColor,
        activeTrackColor = activeColor,
        inactiveTrackColor = inactiveColor,
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(58.dp)
        ) {
            Text(
                text = Theme.onboardingString.progressTitle,
                style = Theme.typography.displayLarge,
                color = Theme.materialColors.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = Theme.onboardingString.progressCaption,
                style = Theme.typography.titleLarge,
                color = Theme.materialColors.onSecondaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            Slider(
                value = animatedValue,
                onValueChange = {},
                enabled = false,
                valueRange = 0f..(stepCount - 1).toFloat(),
                steps = stepCount - 2,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                colors = colors,
                thumb = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.offset(y = (-15).dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = activeColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = Theme.onboardingString.progressLevel(currentStep + 1),
                                style = Theme.typography.labelSmall,
                                color = Theme.materialColors.onPrimary,
                            )
                        }
                        SliderDefaults.Thumb(
                            interactionSource = remember { MutableInteractionSource() },
                            colors = colors,
                            enabled = false,
                        )
                    }
                },
                track = { state ->
                    SliderDefaults.Track(
                        sliderState = state,
                        thumbTrackGapSize = 0.dp,
                        colors = colors,
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProgressPage(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        ProgressPage()
    }
}
