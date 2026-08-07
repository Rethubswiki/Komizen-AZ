package com.komizen.az.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.komizen.az.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.nav_settings)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsSection(title = "General") {
                SettingsSwitchItem(
                    title = stringResource(R.string.auto_update),
                    subtitle = stringResource(R.string.auto_update_summary),
                    checked = uiState.autoUpdate,
                    onCheckedChange = viewModel::setAutoUpdate
                )
            }
            SettingsSection(title = "Apariencia") {
                SettingsClickableItem(
                    title = stringResource(R.string.dark_mode),
                    subtitle = uiState.darkModeLabel,
                    onClick = viewModel::cycleDarkMode
                )
            }
            SettingsSection(title = "Almacenamiento") {
                SettingsClickableItem(
                    title = stringResource(R.string.clear_cache),
                    subtitle = uiState.cacheSize,
                    onClick = viewModel::clearCache
                )
                SettingsClickableItem(
                    title = stringResource(R.string.export_data),
                    subtitle = "Exportar configuración y repos",
                    onClick = viewModel::exportData
                )
                SettingsClickableItem(
                    title = stringResource(R.string.import_data),
                    subtitle = "Importar desde archivo JSON",
                    onClick = viewModel::importData
                )
            }
            SettingsSection(title = "Repositorios") {
                uiState.repos.forEach { repo ->
                    SettingsClickableItem(
                        title = repo.name,
                        subtitle = repo.url,
                        onClick = { /* edit repo */ }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Komizen-AZ v${stringResource(R.string.app_version)}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsClickableItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
