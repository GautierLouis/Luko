package xyz.luko.learning

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.luko.learning.builder.SessionBuilderViewModel
import xyz.luko.learning.congratulation.DebugAction_TriggerEndOfSession
import xyz.luko.learning.congratulation.EndOfSessionCoordinator
import xyz.luko.learning.congratulation.levelup.LevelUpViewModel
import xyz.luko.learning.congratulation.stats.CongratulationViewModel
import xyz.luko.learning.congratulation.streak.GetWeekStreakUseCase
import xyz.luko.learning.congratulation.streak.StreakRefreshViewModel
import xyz.luko.learning.session.CharacterRecognizedUseCase
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.utils.DebugAction

val learningModule =
    module {
        viewModelOf(::SessionBuilderViewModel)
        viewModelOf(::CongratulationViewModel)
        viewModelOf(::SessionViewModel)
        viewModelOf(::StreakRefreshViewModel)
        viewModelOf(::LevelUpViewModel)

        factoryOf(::GetWeekStreakUseCase)
        factoryOf(::CharacterRecognizedUseCase)

        singleOf(::EndOfSessionCoordinator)
    }


val learningDebugModule = module {
    single<DebugAction>(named("learning_eos")) { DebugAction_TriggerEndOfSession(get()) }
}
