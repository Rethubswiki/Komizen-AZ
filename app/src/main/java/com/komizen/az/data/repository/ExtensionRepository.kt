package com.komizen.az.data.repository

import com.komizen.az.data.model.Extension
import com.komizen.az.domain.Result
import kotlinx.coroutines.flow.Flow

interface ExtensionRepository {
    fun getAllExtensions(): Flow<List<Extension>>
    fun getInstalledExtensions(): Flow<List<Extension>>
    fun searchExtensions(query: String): Flow<List<Extension>>
    fun getExtensionCount(): Flow<Int>
    fun getInstalledCount(): Flow<Int>
    suspend fun getExtensionById(id: String): Extension?
    suspend fun refreshExtensions(indexUrl: String): Result<Unit>
    suspend fun installExtension(extension: Extension): Result<Unit>
    suspend fun uninstallExtension(extension: Extension): Result<Unit>
}