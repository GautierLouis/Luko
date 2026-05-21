package xyz.luko.database

import androidx.room.TypeConverter

class RoomTypeConverters {
    @TypeConverter
    fun fromList(list: List<String>?): String? = list?.joinToString(",")

    @TypeConverter
    fun toList(str: String?): List<String>? = str?.split(",")
}
