package com.komizen.az.domain.usecase

import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.Result

class UninstallExtensionUseCase(
    private val repository: ExtensionRepository
) {
    suspend operator fun invoke(extension: Extension): Result<Unit> =
        repository.uninstallExtension(extension)
}