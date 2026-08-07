package com.komizen.az.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.komizen.az.R
import com.komizen.az.ui.screens.browse.BrowseScreen
import com.komizen.az.ui.screens.home.HomeScreen
import com.komizen.az.ui.screens.installed.InstalledScreen
import com.komizen.az.ui.screens.settings.SettingsScreen

sealed class Screen(val route: String, val labelRes: Int, val iconRes: Int) {
    object Home : Screen("home", R.string.nav_home, android.R.drawable.ic_menu_home)
    object Browse : Screen("browse", R.string.nav_browse, android.R.drawable.ic_menu_search)
    object Installed : Screen("installed", R.string.nav_installed, android.R.drawable.ic_menu_manage)
    object Settings : Screen("settings", R.string.nav_settings, android.R.drawable.ic_menu_preferences)
}

val bottomNavItems = listOf(Screen.Home, Screen.Browse, Screen.Installed, Screen.Settings)

@Composable
fun KomizenApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(androidx.compose.material.icons.Icons.Default.Home, contentDescription = null) },
                        label = { Text(stringResource(screen.labelRes)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Browse.route) { BrowseScreen() }
            composable(Screen.Installed.route) { InstalledScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
