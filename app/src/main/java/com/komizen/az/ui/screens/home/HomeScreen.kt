package com.komizen.az.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.komizen.az.ui.components.ErrorMessage
import com.komizen.az.ui.components.ExtensionCard
import com.komizen.az.ui.components.LoadingIndicator
import com.komizen.az.ui.components.StatCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val extensions by viewModel.extensions.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val totalCount by viewModel.totalCount.collectAsStateWithLifecycle()
    val installedCount by viewModel.installedCount.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Komizen-AZ",
            style = MaterialTheme.typography.headlineLarge
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            StatCard(
                title = "Total",
                value = totalCount.toString(),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            StatCard(
                title = "Installed",
                value = installedCount.toString(),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }

        when {
            isLoading -> LoadingIndicator()
            error != null -> ErrorMessage(
                message = error ?: "Unknown error",
                onRetry = { viewModel.refresh() }
            )
            else -> {
                LazyColumn {
                    items(extensions.take(10)) { extension ->
                        ExtensionCard(
                            extension = extension,
                            onClick = { onNavigateToDetail(extension.id) },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}