package xyz.luko.ui.designsystem.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition

@OptIn(ExperimentalMaterial3Api::class)
data class TooltipData(
    val key: OnboardingKey,
    val anchorPosition: TooltipAnchorPosition,
    val cutoutArea: CutoutArea,
)
