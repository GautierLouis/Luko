package xyz.luko.apicontracts.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonObject

object PointDtoSerializer : KSerializer<PointDto> {

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("PointDto", PolymorphicKind.SEALED)

    override fun serialize(encoder: Encoder, value: PointDto) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("PointDtoSerializer can only be used with Json")

        // Reuse the same Json config (ignoreUnknownKeys, etc.) but force compact output
        val compactJson = Json(jsonEncoder.json) { prettyPrint = false }

        val compactString = when (value) {
            is PointDto.Straight -> compactJson.encodeToString(
                PointDto.Straight.serializer(),
                value
            )

            is PointDto.Curved -> compactJson.encodeToString(PointDto.Curved.serializer(), value)
        }

        jsonEncoder.encodeJsonElement(JsonUnquotedLiteral(compactString))
    }

    override fun deserialize(decoder: Decoder): PointDto {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("PointDtoSerializer can only be used with Json")
        val element = jsonDecoder.decodeJsonElement()

        return if (element.jsonObject.containsKey("cp1x")) {
            jsonDecoder.json.decodeFromJsonElement(PointDto.Curved.serializer(), element)
        } else {
            jsonDecoder.json.decodeFromJsonElement(PointDto.Straight.serializer(), element)
        }
    }
}
