package com.komizen.az.data.model

import com.google.gson.annotations.SerializedName

data class ExtensionSource(
    val name: String,
    val lang: String,
    val id: String,
    @SerializedName("baseUrl")
    val baseUrl: String = "",
    @SerializedName("versionId")
    val versionId: Int = 1,
    @SerializedName("hasCloudflare")
    val hasCloudflare: Int = 0
)