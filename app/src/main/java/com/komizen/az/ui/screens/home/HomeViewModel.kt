package com.komizen.az.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.usecase.RefreshExtensionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ExtensionRepository,
    private val refreshUseCase: RefreshExtensionsUseCase
) : ViewModel() {

    private val _extensions = MutableStateFlow<List<Extension>>(emptyList())
    val extensions: StateFlow<List<Extension>> = _extensions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    private val _installedCount = MutableStateFlow(0)
    val installedCount: StateFlow<Int> = _installedCount.asStateFlow()

    init {
        loadExtensions()
        loadCounts()
    }

    private fun loadExtensions() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.getAllExtensions().collect { extensions ->
                _extensions.value = extensions
                _isLoading.value = false
            }
        }
    }

    private fun loadCounts() {
        viewModelScope.launch {
            repository.getExtensionCount().collect { _totalCount.value = it }
        }
        viewModelScope.launch {
            repository.getInstalledCount().collect { _installedCount.value = it }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = refreshUseCase()
            if (result.isError) {
                _error.value = "Failed to refresh extensions"
            }
            _isLoading.value = false
        }
    }
}