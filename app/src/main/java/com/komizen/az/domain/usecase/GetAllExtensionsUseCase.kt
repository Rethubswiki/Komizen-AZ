package com.komizen.az.domain.usecase

import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import kotlinx.coroutines.flow.Flow

class GetAllExtensionsUseCase(
    private val repository: ExtensionRepository
) {
    operator fun invoke(): Flow<List<Extension>> = repository.getAllExtensions()
}