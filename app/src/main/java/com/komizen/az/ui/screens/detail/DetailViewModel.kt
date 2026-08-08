package com.komizen.az.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.usecase.InstallExtensionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val extensionId: String,
    private val repository: ExtensionRepository,
    private val installUseCase: InstallExtensionUseCase
) : ViewModel() {

    private val _extension = MutableStateFlow<Extension?>(null)
    val extension: StateFlow<Extension?> = _extension.asStateFlow()

    init {
        loadExtension()
    }

    private fun loadExtension() {
        viewModelScope.launch {
            _extension.value = repository.getExtensionById(extensionId)
        }
    }

    fun install() {
        viewModelScope.launch {
            _extension.value?.let { ext ->
                installUseCase(ext)
                loadExtension()
            }
        }
    }
}