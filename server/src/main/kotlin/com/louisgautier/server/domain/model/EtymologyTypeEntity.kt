package com.louisgautier.server.domain.model

import kotlinx.serialization.SerialName

enum class EtymologyTypeEntity {
    @SerialName("ideographic")
    IDEOGRAPHIC,

    @SerialName("pictographic")
    PICTOGRAPHIC,

    @SerialName("pictophonetic")
    PICTOPHONETIC,
}