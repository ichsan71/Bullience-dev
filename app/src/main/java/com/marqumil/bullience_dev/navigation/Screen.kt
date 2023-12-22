package com.marqumil.bullience_dev.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Lapor : Screen("Lapor")
    object Profile : Screen("Profile")

    object Splash : Screen("Splash")

    object SignInScreen: Screen("SignInScreen")

    object RegisterScreen: Screen("RegisterScreen")

    object DetailNews : Screen("Home/{newsId}") {
        fun createRoute(newsId: Long) = "Home/$newsId"
    }

    object DetailLapor : Screen("home/{LaporId}") {
        fun createRoute(laporId: Long) = "home/$laporId"
    }

}