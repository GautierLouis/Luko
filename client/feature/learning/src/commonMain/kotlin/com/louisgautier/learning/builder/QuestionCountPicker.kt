package com.louisgautier.learning.builder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.Gray100
import com.louisgautier.designsystem.ai.Gray50
import com.louisgautier.designsystem.ai.Gray700
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.ai.Green700
import org.jetbrains.compose.ui.tooling.preview.Preview

//@CoreKeepForR8
enum class QuestionCount(val value: Int) {
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    TWENTY(20)
}

@Composable
fun QuestionCountPicker(
    questionCount: QuestionCount,
    modifier: Modifier = Modifier,
    onQuestionCountSelected: (QuestionCount) -> Unit = {}
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Gray100),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Number of questions",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuestionCount(
                    count = QuestionCount.FIVE,
                    isSelected = questionCount == QuestionCount.FIVE,
                    onClick = { onQuestionCountSelected(QuestionCount.FIVE) }
                )
                QuestionCount(
                    count = QuestionCount.TEN,
                    isSelected = questionCount == QuestionCount.TEN,
                    onClick = { onQuestionCountSelected(QuestionCount.TEN) }
                )
                QuestionCount(
                    count = QuestionCount.FIFTEEN,
                    isSelected = questionCount == QuestionCount.FIFTEEN,
                    onClick = { onQuestionCountSelected(QuestionCount.FIFTEEN) }
                )
                QuestionCount(
                    count = QuestionCount.TWENTY,
                    isSelected = questionCount == QuestionCount.TWENTY,
                    onClick = { onQuestionCountSelected(QuestionCount.TWENTY) }
                )
            }
        }
    }
}

@Composable
fun QuestionCount(
    count: QuestionCount,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val backgroundColor = if (isSelected) Green50 else Gray50
    val contentColor = if (isSelected) Green700 else Gray700

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .size(56.dp)
            .border(1.dp, contentColor, shape)
            .background(backgroundColor, shape)
            .clip(shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.value.toString(),
            color = contentColor,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun QuestionCountPreview() {
    Column {
        QuestionCount(QuestionCount.FIVE)
        QuestionCount(QuestionCount.TEN, isSelected = true)
    }
}


@Composable
@Preview(showBackground = true)
fun QuestionCountPickerPreview() {
    QuestionCountPicker(
        questionCount = QuestionCount.FIVE,
        onQuestionCountSelected = {}
    )

}