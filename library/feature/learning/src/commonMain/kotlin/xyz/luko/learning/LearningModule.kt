package xyz.luko.learning

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.learning.builder.SessionBuilderViewModel
import xyz.luko.learning.congratulation.SessionCongratulationViewModel
import xyz.luko.learning.list.SessionListViewModel
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase
import xyz.luko.learning.session_details.SessionDetailsViewModel

val learningModule =
    module {
        viewModelOf(::SessionBuilderViewModel)
        viewModel { params -> SessionViewModel(params.get(), get(), get(), get(), get()) }
        viewModelOf(::SessionCongratulationViewModel)
        viewModelOf(::SessionListViewModel)
        viewModel { params -> SessionDetailsViewModel(params.get(), get()) }
        factoryOf(::CalculateScoreUseCase)
        factoryOf(::AccuracyCalculatorUseCase)
    }
