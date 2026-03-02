package com.louisgautier.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.token.color.ColorFamily
import com.louisgautier.designsystem.token.typo.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun DataPicker(
    modifier: Modifier = Modifier,
    title: String = "",
    colorFamily: ColorFamily = AppTheme.colors.grayFamily,
    minValue: Int = 0,
    maxValue: Int = 30,
    step: Int = 1,
    value: Int,
    onValueChange: (Int) -> Unit = {}
) {

    BorderedColumn(
        modifier = modifier,
        colorFamily = colorFamily,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.medium)
            AnimatedCounter(value) {
                Text(text = value.toString(), fontSize = 18.sp, fontWeight = FontWeight.medium)
            }
        }

        AppSlider(
            modifier = Modifier,
            colorFamily = colorFamily,
            minValue = minValue,
            maxValue = maxValue,
            value = value,
            step = step,
            onValueChange = onValueChange
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Preview_DataPicker() {
    AppTheme {
        DataPicker(
            title = "Max. questions:",
            value = 5
        )
    }
}