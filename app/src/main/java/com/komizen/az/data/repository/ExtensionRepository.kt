package com.komizen.az.data.repository

import com.komizen.az.data.local.ExtensionDao
import com.komizen.az.data.model.Extension
import com.komizen.az.data.model.RepoConfig
import com.komizen.az.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ExtensionRepository(
    private val apiService: ApiService,
    private val extensionDao: ExtensionDao,
    private val installer: ExtensionInstaller
) {

    fun getInstalledExtensions(): Flow<List<Extension>> = extensionDao.getInstalled()

    suspend fun fetchExtensions(): List<Extension> = withContext(Dispatchers.IO) {
        val repos = listOf(
            RepoConfig(
                id = "default",
                name = "Komizen Default",
                url = com.komizen.az.BuildConfig.INDEX_URL
            )
        )
        val allExtensions = mutableListOf<Extension>()
        for (repo in repos) {
            try {
                val response = apiService.fetchArrayFlatIndex(repo.url)
                if (response.isSuccessful) {
                    val entries = response.body() ?: emptyList()
                    val extensions = entries.map { entry ->
                        Extension(
                            pkg = entry.pkg,
                            name = entry.name,
                            version = entry.version,
                            versionCode = entry.versionCode,
                            lang = entry.lang,
                            apkUrl = entry.apk,
                            iconUrl = entry.icon,
                            nsfw = entry.nsfw == 1,
                            sources = entry.sources?.map {
                                com.komizen.az.data.model.Source(
                                    id = it.id,
                                    name = it.name,
                                    lang = it.lang,
                                    baseUrl = it.baseUrl
                                )
                            } ?: emptyList(),
                            repoUrl = repo.url
                        )
                    }
                    allExtensions.addAll(extensions)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val deduped = allExtensions
            .groupBy { it.pkg }
            .map { (_, list) -> list.maxBy { it.versionCode } }
            .sortedBy { it.name }

        extensionDao.insertAll(deduped)
        deduped
    }

    suspend fun installExtension(extension: Extension) {
        val result = installer.install(extension.apkUrl, extension.pkg)
        if (result.isSuccess) {
            extensionDao.update(extension.copy(isInstalled = true, installedVersion = extension.version))
        }
    }

    suspend fun updateExtension(extension: Extension) {
        installExtension(extension)
        extensionDao.update(extension.copy(hasUpdate = false, installedVersion = extension.version))
    }

    suspend fun uninstallExtension(extension: Extension) {
        installer.uninstall(extension.pkg)
        extensionDao.update(extension.copy(isInstalled = false, hasUpdate = false, installedVersion = null))
    }

    suspend fun checkForUpdates(): List<Extension> = withContext(Dispatchers.IO) {
        val installed = extensionDao.getInstalled().first()
        val remote = fetchExtensions()
        val updates = installed.mapNotNull { local ->
            remote.find { it.pkg == local.pkg && it.versionCode > local.versionCode }?.copy(
                isInstalled = true,
                hasUpdate = true,
                installedVersion = local.installedVersion
            )
        }
        updates.forEach { extensionDao.update(it) }
        updates
    }
}
