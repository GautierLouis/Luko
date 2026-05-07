package com.louisgautier.apicontracts.dto

import kotlinx.serialization.SerialName

enum class EtymologyTypeDto {
    @SerialName("ideographic")
    IDEOGRAPHIC,

    @SerialName("pictographic")
    PICTOGRAPHIC,

    @SerialName("pictophonetic")
    PICTOPHONETIC,
}
