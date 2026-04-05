package com.louisgautier.database

import androidx.room.TypeConverter
import com.louisgautier.database.entity.EmbeddedResponse
import kotlinx.serialization.json.Json

class RoomTypeConverters {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    @TypeConverter
    fun fromCharList(char: Char): Int {
        return char.code
    }

    @TypeConverter
    fun toCharList(code: Int): Char {
        return Char(code)
    }

    @TypeConverter
    fun fromResponseList(list: List<EmbeddedResponse>?): String? {
        return list?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toResponseList(jsonString: String?): List<EmbeddedResponse>? {
        return jsonString?.let { json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(str: String?): List<String>? {
        return str?.split(",")
    }
}