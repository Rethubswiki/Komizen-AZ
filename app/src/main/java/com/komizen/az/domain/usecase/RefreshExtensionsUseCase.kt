package com.komizen.az.domain.usecase

import com.komizen.az.BuildConfig
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.domain.Result

class RefreshExtensionsUseCase(
    private val repository: ExtensionRepository
) {
    suspend operator fun invoke(indexUrl: String = BuildConfig.INDEX_URL): Result<Unit> =
        repository.refreshExtensions(indexUrl)
}