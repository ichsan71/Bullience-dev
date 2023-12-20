package com.marqumil.bullience_dev.ui.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.marqumil.bullience_dev.R
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.data.remote.response.LoginResponse
import com.marqumil.bullience_dev.navigation.Screen
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
//    val isAuthorized = viewModel.isAuthorized.collectAsStateWithLifecycle()
    val loginData : String? = Hawk.get(SharedPrefs.KEY_LOGIN)

    val navigateToHome = {
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }

    val navigateToOnBoarding = {
        navController.popBackStack()
        navController.navigate(Screen.SignInScreen.route)
    }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        if (loginData != null) {
            navigateToHome()
        } else {
            navigateToOnBoarding()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.width(288.dp),
            painter = painterResource(id = R.drawable.logo_bullience),
            contentDescription = stringResource(
                id = R.string.app_name
            )
        )
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}