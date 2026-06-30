package xyz.luko.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import xyz.luko.ui.designsystem.onboarding.OnboardingKey
import xyz.luko.ui.designsystem.theme.LocalOnboardingState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingTooltipContainer(
    alreadySeen: List<OnboardingKey> = emptyList(),
    enable: Boolean = false,
    onClick: (OnboardingKey) -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    val tooltips by LocalOnboardingState.current

    var index by remember { mutableStateOf(0) }
    val current = tooltips
        .getOrNull(index)
        .takeIf { enable }

    var lastPosition by remember { mutableStateOf(IntOffset.Zero) }


    val tooltipState = rememberTooltipState(
        initialIsVisible = false,
        isPersistent = true,
    )

    LaunchedEffect(current) {
        if (current != null) tooltipState.show()
        else tooltipState.dismiss()
    }

    val clickIntercept = if (current != null) {
        Modifier.clickInterceptor(current)
    } else Modifier

    Box(clickIntercept.fillMaxSize()) {
        TooltipBox(
            state = tooltipState,
            enableUserInput = false,
            onDismissRequest = { },
            positionProvider = remember(current) {
                OnboardingPopupPositionProvider(
                    cutoutArea = current?.cutoutArea,
                    anchorPosition = current?.anchorPosition ?: TooltipAnchorPosition.Above,
                    lastPosition = lastPosition
                ) {
                    lastPosition = it
                }
            },
            tooltip = {
                if (current != null) {
                    OnboardingTooltip(
                        key = current.key,
                        isLast = index == tooltips.lastIndex,
                        onNext = {
                            index++
                            onClick(current.key)
                        },
                        onDismiss = { index = tooltips.size }, // skip to end
                    )
                }
            }
        ) {
            content()
        }

        if (current != null) {
            OnboardingScrim(current)
        }
    }
}

