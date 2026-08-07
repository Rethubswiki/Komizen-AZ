package com.komizen.az.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object JsonUtils {

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    fun prettyPrint(json: String): String {
        return try {
            val element = JsonParser.parseString(json)
            gson.toJson(element)
        } catch (e: Exception) {
            json
        }
    }

    inline fun <reified T> fromJson(json: String): T? {
        return try {
            gson.fromJson(json, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun toJson(obj: Any): String {
        return gson.toJson(obj)
    }
}
