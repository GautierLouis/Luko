package com.louisgautier.utils

import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val utilsPlatformModule: Module

val utilsModule: Module = module {
    includes(utilsPlatformModule)
}