package com.louisgautier.designsystem.ai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(true, onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        content = content
    )
}