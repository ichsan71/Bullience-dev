package com.marqumil.bullience_dev.ui.screen.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marqumil.bullience_dev.ui.common.UiState
import kotlinx.coroutines.flow.observeOn


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel(),
    navigateBack: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {
    LoginContent(
        modifier = modifier,
        onBackClick = navigateBack,
        onNavigateRegister = navigateToRegister,
        onNavigateHome = navigateToHome,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    modifier: Modifier,
    viewModel: SignInViewModel = viewModel(),
    onBackClick: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateHome: () -> Unit,
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val loginState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,

        ) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 0.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
        )
        Text(
            text = "Senang melihatmu kembali",
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.size(8.dp))
        Text(text = "Email", fontWeight = FontWeight.Bold ,fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp, top = 64.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },
            isError = emailErrorState.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp),
            label = {
                Text(text = "Enter Email")
            },
        )
        if (emailErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        Text(text = "Password", fontWeight = FontWeight.Bold ,fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it
            },
            isError = passwordErrorState.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp),
            label = {
                Text(text = "Enter Password")
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = Color.Blue
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (passwordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                when {
                    email.value.text.isEmpty() -> {
                        emailErrorState.value = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }
                    else -> {
                        passwordErrorState.value = false
                        emailErrorState.value = false
                        loginState.value = true
                    }
                }
            },
            content = {
                Text(text = "Login", color = Color.White)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        )

        if (loginState.value) {
            viewModel.pushLogin(email.value.text, password.value.text)

//            PushLogin(email = email.value.text, password = password.value.text, onNavigateHome = onNavigateHome)
            viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        Toast.makeText(LocalContext.current, "Login Success", Toast.LENGTH_SHORT).show()
                        onNavigateHome()
                    }
                    is UiState.Loading -> {
//                Toast.makeText(LocalContext.current, "Loading", Toast.LENGTH_SHORT).show()
                        // make loading Indicator
                        val strokeWidth = 5.dp
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .drawBehind {
                                        drawCircle(
                                            Color.Red,
                                            radius = size.width / 2 - strokeWidth.toPx() / 2,
                                            style = Stroke(strokeWidth.toPx()),
                                            center = center,
                                        )
                                    },
                                color = Color.LightGray,
                                strokeWidth = strokeWidth,
                            )
                        }
                    }
                    is UiState.Error -> {
                        loginState.value = false
                        Toast.makeText(LocalContext.current, "Login Failed ${uiState.errorMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            loginState.value = false
            Log.d("LoginState", "LoginState: false")
        }

        Spacer(Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Belum punya akun? ",
                color = Color.Black)

            Text(
                modifier = Modifier
                    .clickable {
                        onNavigateRegister()
                    },
                text = " Daftar Akun",
                color = Color.Blue,
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navigateBack = {},
        navigateToRegister = {},
        navigateToHome = {}
    )
}