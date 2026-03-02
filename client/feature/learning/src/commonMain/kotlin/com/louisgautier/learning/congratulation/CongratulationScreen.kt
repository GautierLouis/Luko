package com.louisgautier.learning.congratulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.components.button.ElegantButton
import com.louisgautier.designsystem.components.button.ButtonType
import com.louisgautier.designsystem.components.button.ButtonVariant

@Composable
fun CongratulationScreen() {

    var resultButtonVisible by rememberSaveable { mutableStateOf(true) }

    Column(
        Modifier.fillMaxSize().background(AppTheme.colors.yellowFamily.subtle),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Congratulation!",
            fontSize = 22.sp,
            color = AppTheme.colors.orangeFamily.focusRing,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 6.sp
        )

        AnimatedVisibility(resultButtonVisible) {
            ElegantButton(
                variant = ButtonVariant.OUTLINE,
                type = ButtonType.WARNING,
                text = {
                    Text("See result")
                },
                onClick = { }
            )
        }
    }
}