package com.louisgautier.learning

import com.louisgautier.learning.builder.SessionBuilderViewModel
import com.louisgautier.learning.congratulation.SessionCongratulationViewModel
import com.louisgautier.learning.session.SessionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val learningModule = module {
    viewModelOf(::SessionBuilderViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::SessionCongratulationViewModel)
}