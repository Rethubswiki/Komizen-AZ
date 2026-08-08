package com.komizen.az.ui.screens.installed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.komizen.az.ui.components.EmptyState
import com.komizen.az.ui.components.ExtensionCard
import com.komizen.az.ui.components.LoadingIndicator
import org.koin.androidx.compose.koinViewModel

@Composable
fun InstalledScreen(
    viewModel: InstalledViewModel = koinViewModel()
) {
    val extensions by viewModel.installedExtensions.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Installed Extensions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            isLoading -> LoadingIndicator()
            extensions.isEmpty() -> EmptyState("No extensions installed")
            else -> {
                LazyColumn {
                    items(extensions) { extension ->
                        ExtensionCard(
                            extension = extension,
                            onClick = {},
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}