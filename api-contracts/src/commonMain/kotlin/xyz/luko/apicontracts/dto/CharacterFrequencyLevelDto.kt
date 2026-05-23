package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
enum class CharacterFrequencyLevelDto(val value: Int) {
    UNKNOWN(0),
    COMMON(1),
    FREQUENT(2),
    STANDARD(3),
    EXTENDED(4),
    RARE(5),
    OBSOLETE(6);

    companion object {
        fun fromValue(value: Int): CharacterFrequencyLevelDto = when (value) {
            1 -> COMMON
            2 -> FREQUENT
            3 -> STANDARD
            4 -> EXTENDED
            5 -> RARE
            else -> OBSOLETE
        }
    }
}
