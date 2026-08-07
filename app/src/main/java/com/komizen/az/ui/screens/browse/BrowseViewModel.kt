package com.komizen.az.ui.screens.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrowseViewModel(
    private val repository: ExtensionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BrowseUiState())
    val uiState: StateFlow<BrowseUiState> = _uiState.asStateFlow()

    init {
        loadExtensions()
    }

    fun loadExtensions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val extensions = repository.fetchExtensions()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        extensions = extensions,
                        filteredExtensions = extensions
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isBlank()) {
                state.extensions
            } else {
                state.extensions.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.lang.contains(query, ignoreCase = true)
                }
            }
            state.copy(searchQuery = query, filteredExtensions = filtered)
        }
    }

    fun installExtension(extension: Extension) {
        viewModelScope.launch {
            repository.installExtension(extension)
        }
    }

    fun updateExtension(extension: Extension) {
        viewModelScope.launch {
            repository.updateExtension(extension)
        }
    }
}

data class BrowseUiState(
    val isLoading: Boolean = false,
    val extensions: List<Extension> = emptyList(),
    val filteredExtensions: List<Extension> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)
