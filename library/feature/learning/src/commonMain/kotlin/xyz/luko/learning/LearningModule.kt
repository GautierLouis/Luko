package xyz.luko.learning

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import xyz.luko.learning.builder.SessionBuilderViewModel
import xyz.luko.learning.congratulation.SessionCongratulationViewModel
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.learning.session.usecase.AccuracyCalculatorUseCase
import xyz.luko.learning.session.usecase.CalculateScoreUseCase

val learningModule = module {
    viewModelOf(::SessionBuilderViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::SessionCongratulationViewModel)

    factoryOf(::CalculateScoreUseCase)
    factoryOf(::AccuracyCalculatorUseCase)
}