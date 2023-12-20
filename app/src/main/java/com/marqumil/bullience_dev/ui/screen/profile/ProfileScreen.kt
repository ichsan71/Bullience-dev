package com.marqumil.bullience_dev.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.marqumil.bullience_dev.R
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.ui.screen.signin.SignInViewModel
import com.orhanobut.hawk.Hawk

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    navigateToLogin: () -> Unit,
    navController: NavController
) {
    ProfileContent (
        modifier = modifier,
        onNavigateLogin = navigateToLogin,
        navController = navController
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    onNavigateLogin: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(stringResource(R.string.menu_profile))


    }
    Spacer(modifier = Modifier.padding(8.dp))
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                Hawk.delete(SharedPrefs.KEY_LOGIN)
                navController.popBackStack()
                onNavigateLogin()
            },
            content = {
                Text(text = "Keluar", color = Color.White)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        )
    }
}

