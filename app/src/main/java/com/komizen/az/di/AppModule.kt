package com.komizen.az.di

import android.content.Context
import com.komizen.az.data.local.AppDatabase
import com.komizen.az.data.preferences.UserPreferences
import com.komizen.az.data.remote.RetrofitClient
import com.komizen.az.data.repository.ExtensionInstaller
import com.komizen.az.data.repository.ExtensionRepository
import com.komizen.az.ui.screens.browse.BrowseViewModel
import com.komizen.az.ui.screens.installed.InstalledViewModel
import com.komizen.az.ui.screens.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val modules = listOf(
        dataModule,
        viewModelModule
    )

    private val dataModule = module {
        single { androidContext().getSharedPreferences("komizen_secure_prefs", Context.MODE_PRIVATE) }
        single { UserPreferences(androidContext()) }
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().extensionDao() }
        single { RetrofitClient.createDynamic() }
        single { ExtensionInstaller(androidContext()) }
        single { ExtensionRepository(get(), get(), get()) }
    }

    private val viewModelModule = module {
        viewModel { BrowseViewModel(get()) }
        viewModel { InstalledViewModel(get()) }
        viewModel { SettingsViewModel(get()) }
    }
}
