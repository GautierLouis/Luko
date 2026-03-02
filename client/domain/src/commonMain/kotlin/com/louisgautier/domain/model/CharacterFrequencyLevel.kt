package com.louisgautier.domain.model

enum class CharacterFrequencyLevel {
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
