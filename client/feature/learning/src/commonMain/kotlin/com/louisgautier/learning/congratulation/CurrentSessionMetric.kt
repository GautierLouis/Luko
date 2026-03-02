package com.louisgautier.learning.congratulation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.SessionMetric
import com.louisgautier.designsystem.ai.Green100
import com.louisgautier.designsystem.ai.Green700
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CurrentSessionMetric(
    metric: SessionMetric,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Green100, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = metric.icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(Green700)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = metric.title, color = Green700, fontSize = 14.sp)
        }
        Text(
            text = value,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun CurrentSessionMetricPreview() {
    CurrentSessionMetric(SessionMetric.Difficulty, "Hard")
}