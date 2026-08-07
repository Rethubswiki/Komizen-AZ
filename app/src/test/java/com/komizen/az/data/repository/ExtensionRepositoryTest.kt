package com.komizen.az.data.repository

import com.komizen.az.data.local.ExtensionDao
import com.komizen.az.data.model.Extension
import com.komizen.az.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExtensionRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var extensionDao: ExtensionDao
    private lateinit var installer: ExtensionInstaller
    private lateinit var repository: ExtensionRepository

    @Before
    fun setup() {
        apiService = mockk()
        extensionDao = mockk(relaxed = true)
        installer = mockk(relaxed = true)
        repository = ExtensionRepository(apiService, extensionDao, installer)
    }

    @Test
    fun `getInstalledExtensions returns flow from dao`() = runTest {
        val expected = listOf(
            Extension(pkg = "test.pkg", name = "Test", version = "1.0", versionCode = 1, lang = "en", apkUrl = "")
        )
        coEvery { extensionDao.getInstalled() } returns flowOf(expected)

        val result = repository.getInstalledExtensions().first()
        assertEquals(expected, result)
    }
}
