package com.komizen.az.data.model

import com.google.gson.annotations.SerializedName

/**
 * Formato array plano (moderno) — raíz es un array.
 */
typealias ArrayFlatIndex = List<ExtensionEntry>

/**
 * Formato legacy store — raíz es un objeto con campo "sources".
 */
data class LegacyStoreIndex(
    @SerializedName("name") val name: String,
    @SerializedName("sources") val sources: List<ExtensionEntry>
)

data class ExtensionEntry(
    @SerializedName("name") val name: String,
    @SerializedName("pkg") val pkg: String,
    @SerializedName("apk") val apk: String,
    @SerializedName("lang") val lang: String = "all",
    @SerializedName("version") val version: String,
    @SerializedName("versionCode") val versionCode: Int,
    @SerializedName("nsfw") val nsfw: Int = 0,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("sources") val sources: List<SourceEntry>? = null
)

data class SourceEntry(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("lang") val lang: String,
    @SerializedName("baseUrl") val baseUrl: String
)
