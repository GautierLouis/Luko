package xyz.luko.recognition

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val recognitionPlatformModule: Module =
    module {
        singleOf(::AppleCharacterRecognizer) bind CharacterRecognizer::class
    }
