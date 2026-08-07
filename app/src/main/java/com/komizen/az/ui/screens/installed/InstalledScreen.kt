package com.komizen.az.ui.screens.installed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.komizen.az.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstalledScreen(viewModel: InstalledViewModel = koinViewModel()) {
    val extensions by viewModel.installedExtensions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.nav_installed)) })
        }
    ) { padding ->
        if (extensions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.empty_state))
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(extensions, key = { it.pkg }) { ext ->
                    ExtensionCard(
                        extension = ext,
                        onUninstall = { viewModel.uninstallExtension(ext) }
                    )
                }
            }
        }
    }
}
