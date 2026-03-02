package com.louisgautier.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.token.color.ColorFamily

@Composable
fun BorderedColumn(
    modifier: Modifier = Modifier,
    colorFamily: ColorFamily,
    shape: Shape = RoundedCornerShape(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = modifier
            .background(
                color = colorFamily.subtle,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = colorFamily.solid,
                shape = shape
            )
            .padding(16.dp),
        verticalArrangement = verticalArrangement,
        content = content
    )
}