package com.komizen.az.ui.screens.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.usecase.SearchExtensionsUseCase
import com.komizen.az.util.Constants
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BrowseViewModel(
    private val repository: ExtensionRepository,
    private val searchUseCase: SearchExtensionsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _extensions = MutableStateFlow<List<Extension>>(emptyList())
    val extensions: StateFlow<List<Extension>> = _extensions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        observeSearch()
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery
                .debounce(Constants.SEARCH_DEBOUNCE_MS)
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        repository.getAllExtensions()
                    } else {
                        searchUseCase(query)
                    }
                }
                .collect { extensions ->
                    _extensions.value = extensions
                    _isLoading.value = false
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _isLoading.value = true
    }
}