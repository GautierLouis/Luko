package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable(with = PointDtoSerializer::class)
sealed class PointDto {

    abstract val x: Float
    abstract val y: Float
    @Serializable
    data class Straight(
        override val x: Float,
        override val y: Float
    ) : PointDto()

    @Serializable
    data class Curved(
        override val x: Float,
        override val y: Float,
        val cp1x: Float,
        val cp1y: Float,
        val cp2x: Float,
        val cp2y: Float
    ) : PointDto()
}
