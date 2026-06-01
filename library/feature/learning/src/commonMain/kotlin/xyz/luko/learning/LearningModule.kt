package xyz.luko.learning

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.learning.builder.SessionBuilderViewModel
import xyz.luko.learning.congratulation.EndOfSessionViewModel
import xyz.luko.learning.congratulation.StreakRefreshViewModel
import xyz.luko.learning.list.SessionListViewModel
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase

val learningModule =
    module {
        viewModelOf(::SessionBuilderViewModel)
        viewModel { params -> SessionViewModel(params.get(), get(), get(), get(), get()) }
        viewModelOf(::EndOfSessionViewModel)
        viewModelOf(::StreakRefreshViewModel)
        viewModelOf(::SessionListViewModel)
        factoryOf(::CalculateScoreUseCase)
        factoryOf(::AccuracyCalculatorUseCase)
    }
