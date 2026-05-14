package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
enum class CharacterFrequencyLevelDto {
    UNKNOWN,
    COMMON,
    FREQUENT,
    STANDARD,
    EXTENDED,
    RARE,
    OBSOLETE,
    ;

    companion object {
        val validEntry = listOf(COMMON, FREQUENT, STANDARD)
    }
}
