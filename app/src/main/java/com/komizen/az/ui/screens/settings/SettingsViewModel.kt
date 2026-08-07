package com.komizen.az.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.model.RepoConfig
import com.komizen.az.data.preferences.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferences: UserPreferences
) : ViewModel() {

    val autoUpdate = preferences.autoUpdate
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val darkMode = preferences.darkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val repos = preferences.repos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setAutoUpdate(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setAutoUpdate(enabled)
        }
    }

    fun setDarkMode(mode: Int) {
        viewModelScope.launch {
            preferences.setDarkMode(mode)
        }
    }

    fun addRepo(repo: RepoConfig) {
        viewModelScope.launch {
            val current = repos.value.toMutableList()
            if (current.none { it.id == repo.id }) {
                current.add(repo)
                preferences.setRepos(current)
            }
        }
    }

    fun removeRepo(repoId: String) {
        viewModelScope.launch {
            val current = repos.value.filter { it.id != repoId }
            preferences.setRepos(current)
        }
    }
}
