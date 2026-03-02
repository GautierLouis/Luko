package com.louisgautier.apicontracts.dto

import com.louisgautier.apicontracts.KeepForR8
import kotlinx.serialization.Serializable

@KeepForR8
@Serializable
enum class CharacterFrequencyLevelDto {
    UNKNOWN,
    COMMON,
    FREQUENT,
    STANDARD,
    EXTENDED,
    RARE,
    OBSOLETE;
}

