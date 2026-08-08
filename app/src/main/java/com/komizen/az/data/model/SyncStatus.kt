package com.komizen.az.data.model

data class SyncStatus(
    val isSyncing: Boolean = false,
    val lastSyncTime: Long = 0,
    val totalExtensions: Int = 0,
    val errorMessage: String? = null
)