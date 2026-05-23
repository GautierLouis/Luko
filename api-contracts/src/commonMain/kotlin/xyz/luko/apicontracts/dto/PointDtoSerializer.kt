package xyz.luko.apicontracts.dto

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object PointDtoSerializer : JsonContentPolymorphicSerializer<PointDto>(PointDto::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<PointDto> {
        return if (element.jsonObject.containsKey("cp1x"))
            PointDto.Curved.serializer()
        else
            PointDto.Straight.serializer()
    }
}
