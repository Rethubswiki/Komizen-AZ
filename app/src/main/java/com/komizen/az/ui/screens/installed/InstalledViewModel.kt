package com.komizen.az.ui.screens.installed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InstalledViewModel(
    private val repository: ExtensionRepository
) : ViewModel() {

    val installedExtensions = repository.getInstalledExtensions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun uninstallExtension(extension: Extension) {
        viewModelScope.launch {
            repository.uninstallExtension(extension)
        }
    }
}
