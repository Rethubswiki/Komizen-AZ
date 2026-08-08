package com.komizen.az.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "extensions")
data class Extension(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String = "",
    val version: String,
    val author: String = "",
    @SerializedName("download_url")
    val downloadUrl: String = "",
    @SerializedName("icon_url")
    val iconUrl: String? = null,
    val category: String = "general",
    val lang: String = "all",
    val isInstalled: Boolean = false,
    val isNsfw: Boolean = false,
    val size: Long = 0,
    @SerializedName("updated_at")
    val updatedAt: Long = System.currentTimeMillis()
) {
    val displayName: String
        get() = name.ifEmpty { id }

    val categoryLabel: String
        get() = when (category.lowercase()) {
            "manga" -> "📚 Manga"
            "anime" -> "🎬 Anime"
            "novel" -> "📖 Novel"
            "info" -> "ℹ️ Info"
            else -> "📦 General"
        }
}