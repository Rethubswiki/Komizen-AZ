package com.komizen.az.ui.screens.browse

import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BrowseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ExtensionRepository
    private lateinit var viewModel: BrowseViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = BrowseViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `search filters extensions`() = runTest {
        val extensions = listOf(
            Extension("pkg.a", "Alpha", "1.0", 1, "en", ""),
            Extension("pkg.b", "Beta", "1.0", 1, "es", "")
        )
        coEvery { repository.fetchExtensions() } returns extensions
        viewModel.loadExtensions()
        advanceUntilIdle()

        viewModel.onSearchQueryChange("Alpha")
        assertEquals(1, viewModel.uiState.value.filteredExtensions.size)
        assertEquals("Alpha", viewModel.uiState.value.filteredExtensions.first().name)
    }

    @Test
    fun `empty search shows all`() = runTest {
        val extensions = listOf(
            Extension("pkg.a", "Alpha", "1.0", 1, "en", "")
        )
        coEvery { repository.fetchExtensions() } returns extensions
        viewModel.loadExtensions()
        advanceUntilIdle()

        viewModel.onSearchQueryChange("")
        assertEquals(1, viewModel.uiState.value.filteredExtensions.size)
    }
}
