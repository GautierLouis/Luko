package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryWithGraphicDto(
    val dictionary: DictionaryDto,
    val graphics: GraphicDto
)