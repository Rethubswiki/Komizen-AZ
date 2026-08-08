package com.komizen.az.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(
    extensionId: String,
    viewModel: DetailViewModel = koinViewModel { parametersOf(extensionId) }
) {
    val extension by viewModel.extension.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        extension?.let { ext ->
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ext.displayName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Version: ${ext.version}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = ext.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } ?: Text("Extension not found")
    }
}