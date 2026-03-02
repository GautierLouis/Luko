package com.louisgautier.learning.congratulation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.utils.toHHMMSS
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.Route
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SessionCongratulationScreen(
    viewModel: SessionCongratulationViewModel = koinViewModel(),
) {
    val state by viewModel.session.collectAsStateWithLifecycle()
    SessionCongratulationScreen(state)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun SessionCongratulationScreen(
    state: Session?,
) {

    Scaffold(
        containerColor = Green50,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(56.dp))

            AnimatedRewardIcon(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "🎉Congratulation!",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "You completed the quiz!",
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            RewardCard(
                modifier = Modifier.padding(top = 32.dp),
                score = state?.score ?: 0,
                questionCount = (state?.responses?.size ?: 0).toString(),
                time = state?.duration?.toHHMMSS().orEmpty()
            )

            Spacer(Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppButton(
                    onClick = { AppNavigation.navigate(Route.SessionBuilderRoute, true) },
                    modifier = Modifier,
                ) {
                    Text(
                        text = "Start another session",
                        fontSize = 16.sp
                    )
                }

                AppButton(
                    onClick = { AppNavigation.navigateHome() },
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    border = BorderStrokeDefaults.minimum(),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Back to Home",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}