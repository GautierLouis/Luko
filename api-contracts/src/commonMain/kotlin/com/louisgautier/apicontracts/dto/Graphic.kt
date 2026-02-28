package com.louisgautier.apicontracts.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.add
import kotlinx.serialization.json.addJsonArray
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class Graphic(
    val code: Int,
    val strokes: List<String>,
    @Serializable(with = StrokeListSerializer::class)
    val medians: List<Stroke>
) {
    val character: Char
        get() = Char(code)
}

@Serializable
data class Stroke(
    val points: List<Point>
)

@Serializable
data class Point(
    val x: Float,
    val y: Float
)

object StrokeListSerializer : KSerializer<List<Stroke>> {
    override val descriptor = buildClassSerialDescriptor("StrokeList")

    override fun deserialize(decoder: Decoder): List<Stroke> {
        val jsonDecoder = decoder as JsonDecoder
        val array = jsonDecoder.decodeJsonElement().jsonArray

        return array.map { strokeElement ->
            Stroke(
                points = strokeElement.jsonArray.map { pointElement ->
                    val coords = pointElement.jsonArray
                    Point(
                        x = coords[0].jsonPrimitive.float,
                        y = coords[1].jsonPrimitive.float
                    )
                }
            )
        }
    }

    override fun serialize(encoder: Encoder, value: List<Stroke>) {
        val array = buildJsonArray {
            value.forEach { stroke ->
                addJsonArray {
                    stroke.points.forEach { point ->
                        addJsonArray {
                            add(point.x)
                            add(point.y)
                        }
                    }
                }
            }
        }
        (encoder as JsonEncoder).encodeJsonElement(array)
    }
}