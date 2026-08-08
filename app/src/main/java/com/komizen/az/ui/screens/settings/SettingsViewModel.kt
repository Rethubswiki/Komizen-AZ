package com.komizen.az.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.preferences.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferences: UserPreferences
) : ViewModel() {

    val theme: StateFlow<String> = preferences.theme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "system")

    val language: StateFlow<String> = preferences.language
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    val notificationsEnabled: StateFlow<Boolean> = preferences.notificationsEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val autoSyncEnabled: StateFlow<Boolean> = preferences.autoSyncEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun updateTheme(theme: String) {
        viewModelScope.launch { preferences.updateTheme(theme) }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch { preferences.updateLanguage(language) }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch { preferences.updateNotifications(enabled) }
    }

    fun toggleAutoSync(enabled: Boolean) {
        viewModelScope.launch { preferences.updateAutoSync(enabled) }
    }
}