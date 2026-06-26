package xyz.luko.baseui.session

import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.ui.designsystem.components.attrs.FrequencyLevel

fun List<FrequencyLevel>.toDomain(): List<CharacterFrequencyLevel> =
    map {
        when (it) {
            FrequencyLevel.COMMON -> CharacterFrequencyLevel.COMMON
            FrequencyLevel.FREQUENT -> CharacterFrequencyLevel.FREQUENT
            FrequencyLevel.STANDARD -> CharacterFrequencyLevel.STANDARD
        }
    }
