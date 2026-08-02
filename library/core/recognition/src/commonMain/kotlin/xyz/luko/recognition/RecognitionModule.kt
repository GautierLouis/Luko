package xyz.luko.recognition

import org.koin.core.module.Module
import org.koin.dsl.module

val recognitionModule = module {
    includes(recognitionPlatformModule)
}

internal expect val recognitionPlatformModule: Module
