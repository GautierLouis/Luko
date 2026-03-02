package com.louisgautier.composeApp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.AppTitle
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.domain.previewSession
import com.louisgautier.domain.previewStatistics
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.Route
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state.value)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun HomeScreen(
    state: HomeViewModel.UIState,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Green50,
        topBar = { AppTitle("Chinese Drawing Quiz", navigationIcon = {}) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(44.dp)
                ) {
                    StatisticsCard(
                        streak = state.stats.currentDayStreak.toString(),
                        sessions = state.stats.sessionCount.toString(),
                        score = state.stats.totalScore.toString(),
                    )

                    state.lastSession?.let {
                        LastSessionCard(state.lastSession)
                    }
                }

                Spacer(Modifier.weight(1f))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AppButton(
                        modifier = Modifier,
                        onClick = { AppNavigation.navigate(Route.SessionBuilderRoute) }
                    ) {
                        Text("Practice")
                    }
                    AppButton(
                        modifier = Modifier,
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        onClick = { AppNavigation.navigate(Route.DictionaryListRoute) }
                    ) {
                        Text("Dictionary")
                    }
                }
            }
        }
    )
}

@Composable
@Preview()
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeViewModel.UIState(
            isLoading = false,
            lastSession = previewSession,
            stats = previewStatistics
        ),
    )
}


