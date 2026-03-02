package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.Orange500

@Composable
fun LastSessionScore(
    score: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.Bottom) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "+",
                color = Orange500,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = score.toString(),
                color = Orange500,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Text(
            text = " XP",
            color = Orange500,
            fontSize = 10.sp,
            lineHeight = 20.sp,
        )
    }
}