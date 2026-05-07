package com.louisgautier.database

import androidx.room.TypeConverter
import com.louisgautier.database.entity.EmbeddedResponse
import kotlinx.serialization.json.Json

class RoomTypeConverters {
    private val json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
        }

    @TypeConverter
    fun fromCharList(char: Char): Int = char.code

    @TypeConverter
    fun toCharList(code: Int): Char = Char(code)

    @TypeConverter
    fun fromResponseList(list: List<EmbeddedResponse>?): String? =
        list?.let { json.encodeToString(it) }

    @TypeConverter
    fun toResponseList(jsonString: String?): List<EmbeddedResponse>? =
        jsonString?.let { json.decodeFromString(it) }

    @TypeConverter
    fun fromList(list: List<String>?): String? = list?.joinToString(",")

    @TypeConverter
    fun toList(str: String?): List<String>? = str?.split(",")
}
