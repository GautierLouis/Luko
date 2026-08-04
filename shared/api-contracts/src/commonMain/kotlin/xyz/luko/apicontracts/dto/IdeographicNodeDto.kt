package xyz.luko.apicontracts.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class IdeographicNodeDto {
    @Serializable
    @SerialName("operator")
    data class Operator(
        val op: IdeographicCharDto,
        val children: List<IdeographicNodeDto>
    ) : IdeographicNodeDto()

    @Serializable
    @SerialName("glyph")
    data class Glyph(
        val code: Int
    ) : IdeographicNodeDto()

    @Serializable
    @SerialName("unknown")
    data object Unknown : IdeographicNodeDto()
}
