package com.komizen.az.domain.usecase

import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import kotlinx.coroutines.flow.Flow

class SearchExtensionsUseCase(
    private val repository: ExtensionRepository
) {
    operator fun invoke(query: String): Flow<List<Extension>> {
        return if (query.isBlank()) {
            repository.getAllExtensions()
        } else {
            repository.searchExtensions(query)
        }
    }
}