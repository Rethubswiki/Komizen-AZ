package com.komizen.az.data.model

data class RepoConfig(
    val id: String,
    val name: String,
    val url: String,
    val format: RepoFormat = RepoFormat.AUTO,
    val enabled: Boolean = true,
    val lastSync: Long = 0L,
    val entryCount: Int = 0
)

enum class RepoFormat {
    AUTO, ARRAY_FLAT, LEGACY_STORE
}
