package com.louisgautier.learning.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.designsystem.ai.Emerald700
import com.louisgautier.designsystem.ai.Green700
import com.louisgautier.domain.previewDictionary
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Header(
    pager: PagerState,
    char: Dictionary,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Question ${pager.currentPage.inc()} of ${pager.pageCount}")
        LinearProgressIndicator(
            progress = { pager.currentPage.toFloat() / pager.pageCount.toFloat() },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            gapSize = (-10).dp,
            drawStopIndicator = {}
        )
        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Emerald700),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text("Draw Chinese character for:")
                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Green700
                    )
                ) {
                    Text(
                        text = char.pinyin.firstOrNull().orEmpty(),
                        fontSize = 36.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(PagerState(currentPage = 4) { 5 }, previewDictionary)
}