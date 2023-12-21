package com.marqumil.bullience_dev

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marqumil.bullience_dev.navigation.NavigationItem
import com.marqumil.bullience_dev.navigation.Screen
import com.marqumil.bullience_dev.ui.screen.home.HomeScreen
import com.marqumil.bullience_dev.ui.screen.jelajah.JelajahScreen
import com.marqumil.bullience_dev.ui.screen.lapor.LaporScreen
import com.marqumil.bullience_dev.ui.screen.profile.ProfileScreen
import com.marqumil.bullience_dev.ui.screen.register.RegisterScreen
import com.marqumil.bullience_dev.ui.screen.signin.LoginScreen
import com.marqumil.bullience_dev.ui.screen.splash.SplashScreen
import com.marqumil.bullience_dev.ui.theme.BullienceTheme
import com.orhanobut.hawk.Hawk


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BullienceApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // build hawk init
    Hawk.init(LocalContext.current).build()

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailLapor.route
                && currentRoute != Screen.SignInScreen.route
                && currentRoute != Screen.Splash.route
                && currentRoute != Screen.RegisterScreen.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToLapor = {
                        navController.navigate(Screen.Lapor.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailLapor.route + "/$id") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Jelajah.route) {
                JelajahScreen()
            }
            composable(Screen.Lapor.route) {
                LaporScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    navigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Screen.SignInScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(Screen.RegisterScreen.route) {
                RegisterScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Screen.SignInScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navigateToLoginAsReg = {
                        navController.popBackStack()
                        navController.navigate(Screen.SignInScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                )
            }
            composable(Screen.SignInScreen.route) {
                LoginScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToRegister = {
                        navController.popBackStack()
                        navController.navigate(Screen.RegisterScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navigateToHome = {
                        navController.navigate(Screen.Home.route) {
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

//    NavHost(navController = navController, startDestination = "splash_screen") {
//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
//        composable("main_screen") {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "Main_Screen", color = Color.Black)
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    BullienceTheme {
        BullienceApp()
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_lapor),
                icon = Icons.Default.Warning,
                screen = Screen.Lapor
            ),
            NavigationItem(
                title = stringResource(R.string.menu_jelajah),
                icon = ImageVector.vectorResource(id = R.drawable.baseline_library_books_24),
                screen = Screen.Jelajah
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}