package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.domain.model.CharacterFrequencyLevel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterGroupPicker(
    modifier: Modifier = Modifier,
    selectedLevels: Set<CharacterFrequencyLevel> = emptySet(),
    onClick: (CharacterFrequencyLevel) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CharacterFrequencyLevel.validEntry.forEach {
            CharacterLevelCard(
                level = it,
                selected = it in selectedLevels,
                onClick = { onClick(it) }
            )
        }
    }
}

@Composable
@Preview
fun CharacterGroupPickerPreview() {
    MaterialTheme {
        CharacterGroupPicker()
    }
}