package com.louisgautier.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CountPicker(
    modifier: Modifier = Modifier,
    initialCount: Int = 1,
    maxValue: Int = 30,
    onValueChange: (Int) -> Unit = {}
) {

    var count by remember { mutableIntStateOf(initialCount) }

    val minEnabled = count > 1
    val maxEnabled = count < maxValue

    val colorFamily = AppTheme.colors.grayFamily

    val enabledBorder = BorderStroke(1.dp, colorFamily.solid)
    val disabledBorder = BorderStroke(1.dp, colorFamily.solid.copy(alpha = .1f))

    val shape = RoundedCornerShape(8.dp)

    val colors = ButtonDefaults.buttonColors(
        containerColor = colorFamily.subtle,
        contentColor = colorFamily.solid,
        disabledContainerColor = colorFamily.emphasized,
        disabledContentColor = colorFamily.solid.copy(alpha = .1f)
    )

    Row(
        modifier = modifier.wrapContentHeight().fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Button(
            shape = shape,
            border = if (minEnabled) enabledBorder else disabledBorder,
            onClick = {
                count = (count - 1).coerceAtLeast(1)
                onValueChange(count)
            },
            enabled = minEnabled,
            colors = colors,
        ) {
            Text("-")
        }

        Button(
            onClick = {},
            shape = shape,
            border = enabledBorder,
            enabled = false,
            modifier = Modifier.clearAndSetSemantics {},
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = colorFamily.subtle,
                disabledContentColor = colorFamily.solid
            )
        ) {
            AnimatedCounter(value = count) {
                Text(it.toString())
            }
        }

        Button(
            shape = shape,
            border = if (maxEnabled) enabledBorder else disabledBorder,
            onClick = {
                count = (count + 1).coerceAtMost(maxValue)
                onValueChange(count)
            },
            enabled = maxEnabled,
            colors = colors
        ) {
            Text("+")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview_CountPicker() {
    AppTheme {
        Box {
            CountPicker()
        }
    }
}