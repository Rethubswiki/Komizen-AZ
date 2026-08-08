package com.komizen.az.ui.navigation

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object Browse : NavRoutes("browse")
    data object Installed : NavRoutes("installed")
    data object Dashboard : NavRoutes("dashboard")
    data object Settings : NavRoutes("settings")
    data object Detail : NavRoutes("detail/{extensionId}") {
        fun createRoute(extensionId: String) = "detail/$extensionId"
    }
}