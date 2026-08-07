package com.komizen.az.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.komizen.az.data.model.Source

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromSourceList(sources: List<Source>): String {
        return gson.toJson(sources)
    }

    @TypeConverter
    fun toSourceList(json: String): List<Source> {
        val type = object : TypeToken<List<Source>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}
