package com.komizen.az.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.komizen.az.ui.navigation.NavRoutes
import com.komizen.az.ui.screens.browse.BrowseScreen
import com.komizen.az.ui.screens.dashboard.DashboardScreen
import com.komizen.az.ui.screens.detail.DetailScreen
import com.komizen.az.ui.screens.home.HomeScreen
import com.komizen.az.ui.screens.installed.InstalledScreen
import com.komizen.az.ui.screens.settings.SettingsScreen

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun KomizenApp() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(NavRoutes.Home.route, Icons.Filled.Home, "Home"),
        BottomNavItem(NavRoutes.Browse.route, Icons.Filled.Search, "Browse"),
        BottomNavItem(NavRoutes.Installed.route, Icons.Filled.Extension, "Installed"),
        BottomNavItem(NavRoutes.Dashboard.route, Icons.Filled.Dashboard, "Dashboard"),
        BottomNavItem(NavRoutes.Settings.route, Icons.Filled.Settings, "Settings")
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
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
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavRoutes.Home.route) {
                HomeScreen(
                    onNavigateToDetail = { extensionId ->
                        navController.navigate(NavRoutes.Detail.createRoute(extensionId))
                    }
                )
            }
            composable(NavRoutes.Browse.route) {
                BrowseScreen(
                    onNavigateToDetail = { extensionId ->
                        navController.navigate(NavRoutes.Detail.createRoute(extensionId))
                    }
                )
            }
            composable(NavRoutes.Installed.route) {
                InstalledScreen()
            }
            composable(NavRoutes.Dashboard.route) {
                DashboardScreen()
            }
            composable(NavRoutes.Settings.route) {
                SettingsScreen()
            }
            composable(NavRoutes.Detail.route) { backStackEntry ->
                DetailScreen(
                    extensionId = backStackEntry.arguments?.getString("extensionId") ?: ""
                )
            }
        }
    }
}