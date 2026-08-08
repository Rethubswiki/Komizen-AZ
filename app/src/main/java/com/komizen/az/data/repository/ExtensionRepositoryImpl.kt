package com.komizen.az.data.repository

import com.komizen.az.data.local.ExtensionDao
import com.komizen.az.data.model.Extension
import com.komizen.az.data.remote.ApiService
import com.komizen.az.domain.Result
import kotlinx.coroutines.flow.Flow

class ExtensionRepositoryImpl(
    private val dao: ExtensionDao,
    private val apiService: ApiService
) : ExtensionRepository {

    override fun getAllExtensions(): Flow<List<Extension>> = dao.getAllExtensions()

    override fun getInstalledExtensions(): Flow<List<Extension>> = dao.getInstalledExtensions()

    override fun searchExtensions(query: String): Flow<List<Extension>> {
        return dao.searchExtensions(query.trim())
    }

    override fun getExtensionCount(): Flow<Int> = dao.getExtensionCount()

    override fun getInstalledCount(): Flow<Int> = dao.getInstalledCount()

    override suspend fun getExtensionById(id: String): Extension? = dao.getExtensionById(id)

    override suspend fun refreshExtensions(indexUrl: String): Result<Unit> {
        return Result.runCatchingSuspend {
            val index = apiService.fetchIndex(indexUrl)
            dao.insertExtensions(index.extensions)
        }
    }

    override suspend fun installExtension(extension: Extension): Result<Unit> {
        return Result.runCatchingSuspend {
            dao.update(extension.copy(isInstalled = true))
        }
    }

    override suspend fun uninstallExtension(extension: Extension): Result<Unit> {
        return Result.runCatchingSuspend {
            dao.update(extension.copy(isInstalled = false))
        }
    }
}