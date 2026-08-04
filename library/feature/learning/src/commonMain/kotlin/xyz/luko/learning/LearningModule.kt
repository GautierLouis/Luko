package xyz.luko.learning

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.learning.builder.SessionBuilderViewModel
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import xyz.luko.learning.congratulation.stats.CongratulationViewModel
import xyz.luko.learning.congratulation.streak.StreakListUseCase
import xyz.luko.learning.congratulation.streak.StreakRefreshViewModel
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase

val learningModule =
    module {
        viewModelOf(::SessionBuilderViewModel)
        viewModelOf(::CongratulationViewModel)
        viewModel { params ->
            SessionViewModel(
                params = params.get(),
                repository = get(),
                sessionRepository = get(),
                analyzeUserDrawing = get(),
                coordinator = get(),
                appConfig = get(),
                recognizer = get(),
            )
        }
        viewModelOf(::StreakRefreshViewModel)

        factoryOf(::AccuracyCalculatorUseCase)
        factoryOf(::StreakListUseCase)

        singleOf(::EndOfSessionCoordinator)
    }
