package com.komizen.az.domain.usecase

import com.komizen.az.data.model.Extension
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.Result

class InstallExtensionUseCase(
    private val repository: ExtensionRepository
) {
    suspend operator fun invoke(extension: Extension): Result<Unit> =
        repository.installExtension(extension)
}