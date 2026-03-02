package com.louisgautier.learning

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.Gray100
import com.louisgautier.domain.model.Difficulty
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DifficultyPickerContent(
    modifier: Modifier = Modifier,
    selectedDifficulty: Difficulty = Difficulty.EASY,
    onDifficultySelected: (Difficulty) -> Unit = {}
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
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Pick a difficulty",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            Difficulty.entries.forEach {
                DifficultyCard(
                    difficulty = it,
                    selected = selectedDifficulty == it,
                    onClick = { onDifficultySelected(it) }
                )
            }
        }
    }
}

@Composable
@Preview
fun Preview_DifficultyPickerContent() {
    MaterialTheme {
        DifficultyPickerContent()
    }
}
