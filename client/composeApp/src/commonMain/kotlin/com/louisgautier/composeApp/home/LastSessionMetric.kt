package com.louisgautier.composeApp.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.Gray100
import com.louisgautier.designsystem.ai.Gray400
import com.louisgautier.designsystem.SessionMetric
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LastSessionMetric(
    metric: SessionMetric,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Gray100, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = metric.icon,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                colorFilter = ColorFilter.tint(Gray400)
            )
            Text(text = metric.title, color = Gray400, fontSize = 12.sp)
        }
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun LastSessionMetricPreview() {
    LastSessionMetric(SessionMetric.Difficulty, "Hard")
}