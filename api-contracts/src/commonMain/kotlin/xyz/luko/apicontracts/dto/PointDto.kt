package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable(with = PointDtoSerializer::class)
sealed class PointDto {

    @Serializable
    data class Straight(
        val x: Float,
        val y: Float
    ) : PointDto()

    @Serializable
    data class Curved(
        val x: Float,
        val y: Float,
        val cp1x: Float,
        val cp1y: Float,
        val cp2x: Float,
        val cp2y: Float
    ) : PointDto()
}
