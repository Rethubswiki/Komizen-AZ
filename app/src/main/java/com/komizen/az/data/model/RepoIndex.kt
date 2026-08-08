package com.komizen.az.data.model

import com.google.gson.annotations.SerializedName

data class RepoIndex(
    val version: Int = 1,
    val name: String = "",
    val website: String = "",
    @SerializedName("generated_at")
    val generatedAt: String = "",
    val extensions: List<Extension> = emptyList()
)