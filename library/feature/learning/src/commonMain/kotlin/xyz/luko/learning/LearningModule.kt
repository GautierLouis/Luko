package xyz.luko.learning

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.learning.builder.SessionBuilderViewModel
import xyz.luko.learning.congratulation.EndOfSessionUseCase
import xyz.luko.learning.congratulation.streak.StreakListUseCase
import xyz.luko.learning.congratulation.streak.StreakRefreshViewModel
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase

val learningModule =
    module {
        viewModelOf(::SessionBuilderViewModel)
        viewModel { params ->
            SessionViewModel(
                params = params.get(),
                repository = get(),
                sessionRepository = get(),
                analyzeUserDrawing = get(),
                scoreCalculator = get(),
                endOfSessionUseCase = get()
            )
        }
        viewModelOf(::StreakRefreshViewModel)
        factoryOf(::CalculateScoreUseCase)
        factoryOf(::AccuracyCalculatorUseCase)
        factoryOf(::StreakListUseCase)
        factoryOf(::EndOfSessionUseCase)
    }
