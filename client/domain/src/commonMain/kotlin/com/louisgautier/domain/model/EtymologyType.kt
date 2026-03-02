package com.louisgautier.domain.model

import kotlinx.serialization.SerialName

enum class EtymologyType {
    @SerialName("ideographic")
    IDEOGRAPHIC,

    @SerialName("pictographic")
    PICTOGRAPHIC,

    @SerialName("pictophonetic")
    PICTOPHONETIC,
}