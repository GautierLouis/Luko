package xyz.luko.apicontracts.dto

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class GraphicDto(
    val code: Int,
    val strokes: List<String>,
    val medians: List<StrokeDto>,
    val smootherMedians: List<StrokeDto>
)

@Serializable
data class StrokeDto(
    val points: List<PointDto>,
)

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

object PointDtoSerializer : JsonContentPolymorphicSerializer<PointDto>(PointDto::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<PointDto> {
        return if (element.jsonObject.containsKey("cp1x"))
            PointDto.Curved.serializer()
        else
            PointDto.Straight.serializer()
    }
}
