package com.komizen.az.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.RepoConfig
import com.komizen.az.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferences.autoUpdate.collect { auto ->
                _uiState.update { it.copy(autoUpdate = auto) }
            }
        }
        viewModelScope.launch {
            preferences.darkMode.collect { mode ->
                _uiState.update {
                    it.copy(
                        darkMode = mode,
                        darkModeLabel = when (mode) {
                            0 -> "Seguir sistema"
                            1 -> "Oscuro"
                            else -> "Claro"
                        }
                    )
                }
            }
        }
        viewModelScope.launch {
            preferences.repos.collect { repos ->
                _uiState.update { it.copy(repos = repos) }
            }
        }
    }

    fun setAutoUpdate(enabled: Boolean) {
        viewModelScope.launch { preferences.setAutoUpdate(enabled) }
    }

    fun cycleDarkMode() {
        viewModelScope.launch {
            val next = (_uiState.value.darkMode + 1) % 3
            preferences.setDarkMode(next)
        }
    }

    fun clearCache() {
        // Implementación delegada al repositorio
    }

    fun exportData() {
        // Exportar a JSON
    }

    fun importData() {
        // Importar desde JSON
    }
}

data class SettingsUiState(
    val autoUpdate: Boolean = false,
    val darkMode: Int = 0,
    val darkModeLabel: String = "Seguir sistema",
    val cacheSize: String = "0 MB",
    val repos: List<RepoConfig> = emptyList()
)
