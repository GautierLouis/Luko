package com.louisgautier.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.louisgautier.designsystem.ai.Teal600
import com.louisgautier.designsystem.modifier.borderedBackground
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterFrequencyLevelPicker(
    selectedTabIndex: Int,
    onTabSelected: (DictionaryTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val locale = Locale.current

    SecondaryTabRow(
        modifier = modifier
            .fillMaxWidth()
            .borderedBackground(
                BorderStrokeDefaults.minimum(),
                Color.White,
                ShapeDefaults.card()
            )
            .padding(4.dp),
        containerColor = Color.Transparent,
        selectedTabIndex = selectedTabIndex,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .fillMaxHeight()
                    .zIndex(-1f)
                    .tabIndicatorOffset(selectedTabIndex)
                    .clip(ShapeDefaults.card()),
                color = Teal600,
            )
        },
        divider = {}
    ) {
        DictionaryTab.entries.forEachIndexed { index, level ->
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .background(Color.Transparent, ShapeDefaults.card())
                    .clip(ShapeDefaults.card())
                    .clickable(
                        onClick = { onTabSelected(level) },
                        role = Role.Tab
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level.name.lowercase().capitalize(locale),
                    textAlign = TextAlign.Center,
                    color = if (selectedTabIndex == index) Color.White else Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun CharacterFrequencyLevelPickerPreview() {
    CharacterFrequencyLevelPicker(DictionaryTab.COMMON.ordinal, {})
}