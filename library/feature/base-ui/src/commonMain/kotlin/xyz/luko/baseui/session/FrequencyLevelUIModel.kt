package xyz.luko.baseui.session

import xyz.luko.designsystem.components.attrs.FrequencyLevel
import xyz.luko.domain.model.CharacterFrequencyLevel

fun List<FrequencyLevel>.toDomain(): List<CharacterFrequencyLevel> =
    map {
        when (it) {
            FrequencyLevel.COMMON -> CharacterFrequencyLevel.COMMON
            FrequencyLevel.FREQUENT -> CharacterFrequencyLevel.FREQUENT
            FrequencyLevel.STANDARD -> CharacterFrequencyLevel.STANDARD
        }
    }
