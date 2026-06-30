package xyz.luko.ui.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import xyz.luko.ui.designsystem.onboarding.CutoutArea
import xyz.luko.ui.designsystem.onboarding.CutoutArea.Circle
import xyz.luko.ui.designsystem.onboarding.CutoutArea.Rounded
import xyz.luko.ui.designsystem.onboarding.OnboardingKey
import xyz.luko.ui.designsystem.onboarding.TooltipData
import xyz.luko.ui.designsystem.theme.LocalOnboardingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Modifier.registerTooltip(
    key: OnboardingKey,
    anchorPosition: TooltipAnchorPosition,
): Modifier {
    var tooltips by LocalOnboardingState.current
    val density = LocalDensity.current

    DisposableEffect(key) {
        onDispose {
            tooltips = tooltips.filter { it.key != key }
        }
    }

    return this.onGloballyPositioned { coords ->
        val cutout = createCutout(key, coords.boundsInRoot(), density)
        val existing = tooltips.indexOfFirst { it.key == key }
        tooltips = tooltips.toMutableList().apply {
            if (existing >= 0) set(
                existing,
                TooltipData(key, anchorPosition, cutout)
            )
            else add(TooltipData(key, anchorPosition, cutout))
        }
    }
}

private fun createCutout(
    key: OnboardingKey,
    rect: Rect,
    density: Density
): CutoutArea {
    return when (key) {
        OnboardingKey.HOME_MENU -> Circle(
            center = rect.center,
            radius = with(density) { 50.dp.toPx() }
        )

        OnboardingKey.OVERALL_STATS -> Rounded(
            rect = rect.inflate(12f),
            radius = with(density) { 16.dp.toPx() }
        )
    }
}
