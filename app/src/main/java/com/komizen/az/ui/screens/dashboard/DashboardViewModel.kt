package com.komizen.az.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komizen.az.data.repository.ExtensionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardStats(
    val totalExtensions: Int = 0,
    val installedExtensions: Int = 0,
    val activeSources: Int = 0,
    val pendingUpdates: Int = 0
)

class DashboardViewModel(
    private val repository: ExtensionRepository
) : ViewModel() {

    private val _stats = MutableStateFlow(DashboardStats())
    val stats: StateFlow<DashboardStats> = _stats.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            repository.getAllExtensions().collect { extensions ->
                val installed = extensions.count { it.isInstalled }
                val sources = extensions.map { it.category }.distinct().size

                _stats.value = DashboardStats(
                    totalExtensions = extensions.size,
                    installedExtensions = installed,
                    activeSources = sources,
                    pendingUpdates = 0
                )
            }
        }
    }
}