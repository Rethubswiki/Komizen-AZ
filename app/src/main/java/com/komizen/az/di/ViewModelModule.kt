package com.komizen.az.di

import com.komizen.az.ui.screens.browse.BrowseViewModel
import com.komizen.az.ui.screens.dashboard.DashboardViewModel
import com.komizen.az.ui.screens.detail.DetailViewModel
import com.komizen.az.ui.screens.home.HomeViewModel
import com.komizen.az.ui.screens.installed.InstalledViewModel
import com.komizen.az.ui.screens.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { BrowseViewModel(get(), get()) }
    viewModel { (extensionId: String) -> DetailViewModel(extensionId, get(), get()) }
    viewModel { InstalledViewModel(get(), get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}