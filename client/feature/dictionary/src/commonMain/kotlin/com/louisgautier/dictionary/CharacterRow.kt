package com.louisgautier.dictionary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.domain.previewSimpleDictionary
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.domain.model.SimpleDictionary
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterRow(
    dictionary: SimpleDictionary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .clip(ShapeDefaults.card())
            .clickable(
                role = Role.Button,
                onClickLabel = dictionary.pinyin.first(),
                onClick = { onClick() },
            ),
        shape = ShapeDefaults.card(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStrokeDefaults.minimum()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(12.dp)
                .clip(ShapeDefaults.card()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = dictionary.character.toString(),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dictionary.pinyin.first(),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterRow() {
    CharacterRow(
        previewSimpleDictionary,
        modifier = Modifier
            .width(100.dp)
            .aspectRatio(1f)
    )
}