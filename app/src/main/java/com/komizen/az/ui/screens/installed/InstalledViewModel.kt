package com.komizen.az.ui.screens.installed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.usecase.UninstallExtensionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InstalledViewModel(
    private val repository: ExtensionRepository,
    private val uninstallUseCase: UninstallExtensionUseCase
) : ViewModel() {

    private val _installedExtensions = MutableStateFlow<List<Extension>>(emptyList())
    val installedExtensions: StateFlow<List<Extension>> = _installedExtensions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadInstalledExtensions()
    }

    private fun loadInstalledExtensions() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getInstalledExtensions().collect { extensions ->
                _installedExtensions.value = extensions
                _isLoading.value = false
            }
        }
    }

    fun uninstall(extension: Extension) {
        viewModelScope.launch {
            uninstallUseCase(extension)
        }
    }
}