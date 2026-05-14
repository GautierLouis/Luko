package xyz.luko.server.domain.model

enum class CharacterFrequencyLevelEntity {
    UNKNOWN,
    COMMON,
    FREQUENT,
    STANDARD,
    EXTENDED,
    RARE,
    OBSOLETE;

    companion object {
        val validEntry = listOf(COMMON, FREQUENT, STANDARD)
    }
}