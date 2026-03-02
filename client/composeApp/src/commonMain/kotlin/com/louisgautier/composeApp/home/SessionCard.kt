package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.SessionMetric
import com.louisgautier.designsystem.ai.Teal50
import com.louisgautier.designsystem.ai.Teal600
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.previewSession
import com.louisgautier.domain.utils.toHHMMSS
import com.louisgautier.domain.utils.toISODateString
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun SessionCard(
    session: Session,
    modifier: Modifier = Modifier
) {
    val locale = Locale.current

    HomeCard(modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RoundIcon(
                icon = AppIcon.RoundedTrophy,
                containerColor = Teal50,
                contentColor = Teal600,
                size = 24.dp,
                padding = 4.dp
            )
            Text(text = session.date.toISODateString())
            Spacer(Modifier.weight(1f))
            LastSessionScore(session.score)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LastSessionMetric(
                metric = SessionMetric.QuestionCount,
                value = session.responses.size.toString(),
                modifier = Modifier.weight(1f)
            )
            LastSessionMetric(
                metric = SessionMetric.Time,
                value = session.duration.toHHMMSS(),
                modifier = Modifier.weight(1f)
            )
            LastSessionMetric(
                metric = SessionMetric.Difficulty,
                value = session.difficulty.name.lowercase().capitalize(locale),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun SessionCardPreview() {
    SessionCard(session = previewSession)
}