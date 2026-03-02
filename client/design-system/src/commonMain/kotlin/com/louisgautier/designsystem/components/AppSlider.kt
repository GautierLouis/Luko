package com.louisgautier.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.token.color.ColorFamily
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSlider(
    modifier: Modifier = Modifier,
    colorFamily: ColorFamily = AppTheme.colors.grayFamily,
    minValue: Int = 0,
    maxValue: Int = 30,
    value: Int,
    step: Int = 5,
    onValueChange: (Int) -> Unit = {}
) {

    Slider(
        modifier = modifier,
        value = value.toFloat(),
        onValueChange = { onValueChange(it.toInt()) },
        valueRange = minValue.toFloat()..maxValue.toFloat(),
        steps = step,
        colors = SliderDefaults.colors(
            thumbColor = colorFamily.solid,
            activeTrackColor = colorFamily.solid,
            inactiveTrackColor = colorFamily.solid.copy(alpha = .1f),
            activeTickColor = colorFamily.muted,
            inactiveTickColor = colorFamily.solid.copy(alpha = .1f)
        )
    )
}

@Composable
@Preview(showBackground = true)
fun Preview_Stepper() {
    AppTheme {
        AppSlider(
            value = 5
        )
    }
}