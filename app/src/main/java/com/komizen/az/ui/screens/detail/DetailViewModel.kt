package com.komizen.az.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ExtensionRepository
) : ViewModel() {

    fun install(extension: Extension) {
        viewModelScope.launch {
            repository.installExtension(extension)
        }
    }
}
