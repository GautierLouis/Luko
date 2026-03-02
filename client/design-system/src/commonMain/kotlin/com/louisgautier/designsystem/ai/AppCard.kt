package com.louisgautier.designsystem.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit,
    icon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    label: @Composable () -> Unit = {},
) {
    DefaultCard(
        modifier,
        borderColor = borderColor,
        containerColor = backgroundColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
        ) {
            icon()
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                title()
                label()
            }
        }
    }
}