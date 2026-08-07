package com.komizen.az.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "extensions")
data class Extension(
    @PrimaryKey
    val pkg: String,
    val name: String,
    val version: String,
    val versionCode: Int,
    val lang: String,
    val apkUrl: String,
    val iconUrl: String? = null,
    val nsfw: Boolean = false,
    val sources: List<Source> = emptyList(),
    val isInstalled: Boolean = false,
    val hasUpdate: Boolean = false,
    val installedVersion: String? = null,
    val repoUrl: String = "",
    val lastUpdated: Long = System.currentTimeMillis()
)

data class Source(
    val id: String,
    val name: String,
    val lang: String,
    val baseUrl: String
)
