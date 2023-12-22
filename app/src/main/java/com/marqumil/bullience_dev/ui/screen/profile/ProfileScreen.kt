package com.marqumil.bullience_dev.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.marqumil.bullience_dev.R
import com.marqumil.bullience_dev.ViewModelFactory
import com.marqumil.bullience_dev.data.di.Injection
import com.marqumil.bullience_dev.data.local.SharedPrefs
import com.marqumil.bullience_dev.ui.common.UiState
import com.marqumil.bullience_dev.ui.screen.home.HomeViewModel
import com.marqumil.bullience_dev.ui.screen.signin.SignInViewModel
import com.orhanobut.hawk.Hawk

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
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
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onNavigateLogin: () -> Unit,
    navController: NavController
) {

    var email = ""
    var username = ""

    viewModel.getUser()
    val userValue by viewModel.uiState.collectAsState(initial = UiState.Loading)

    when (userValue) {
        is UiState.Success -> {
            val data = (userValue as UiState.Success).data
            email = data.user?.email.toString()
            username = data.user?.username.toString()
            Log.d("UserState", "Success $email")
        }
        is UiState.Loading -> {
            Log.d("UserState", "Loading try")
            email = "Loading"
        }
        is UiState.Error -> {
            Log.d("UserState", "Error ${(userValue as UiState.Error).errorMessage}")
            email = "Error"
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Username : $username",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Email : $email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontSize = 16.sp
            )
        }

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

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileContent(
        onNavigateLogin = {},
        navController = {} as NavController
    )
}

