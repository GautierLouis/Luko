package xyz.luko.ui.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import xyz.luko.ui.designsystem.onboarding.OnboardingKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TooltipScope.OnboardingTooltip(
    key: OnboardingKey,
    isLast: Boolean,
    onNext: () -> Unit,
    onDismiss: () -> Unit,
) {
    RichTooltip(
        title = { Text(key.title()) },
        action = {
            TextButton(onClick = if (isLast) onDismiss else onNext) {
                Text(if (isLast) "Got it" else "Next")
            }
        },
    ) {
        Text(key.desc())
    }
}
