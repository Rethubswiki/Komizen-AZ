package com.komizen.az.di

import com.komizen.az.data.preferences.UserPreferences
import com.komizen.az.data.repository.ExtensionInstaller
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.data.repository.ExtensionRepositoryImpl
import com.komizen.az.domain.usecase.GetAllExtensionsUseCase
import com.komizen.az.domain.usecase.GetInstalledExtensionsUseCase
import com.komizen.az.domain.usecase.InstallExtensionUseCase
import com.komizen.az.domain.usecase.RefreshExtensionsUseCase
import com.komizen.az.domain.usecase.SearchExtensionsUseCase
import com.komizen.az.domain.usecase.UninstallExtensionUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<ExtensionRepository> { ExtensionRepositoryImpl(get(), get()) }
    single { ExtensionInstaller(androidContext()) }
    single { UserPreferences(androidContext()) }

    factory { GetAllExtensionsUseCase(get()) }
    factory { GetInstalledExtensionsUseCase(get()) }
    factory { SearchExtensionsUseCase(get()) }
    factory { InstallExtensionUseCase(get()) }
    factory { UninstallExtensionUseCase(get()) }
    factory { RefreshExtensionsUseCase(get()) }
}