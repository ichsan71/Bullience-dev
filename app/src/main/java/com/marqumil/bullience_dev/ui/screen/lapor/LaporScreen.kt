package com.marqumil.bullience_dev.ui.screen.lapor

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marqumil.bullience_dev.R
import com.marqumil.bullience_dev.ui.common.UiState
import com.marqumil.bullience_dev.ui.screen.signin.SignInViewModel

@Composable
fun LaporScreen(
    modifier: Modifier = Modifier,
    viewModel: LaporViewModel = LaporViewModel(),
    onNavigateDetail: () -> Unit = {}
) {
    LaporContent(
        modifier = modifier,
        viewModel = viewModel,
        navigateToDetail = onNavigateDetail
        )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporContent(
    modifier: Modifier = Modifier,
    viewModel: LaporViewModel = LaporViewModel(),
    navigateToDetail: () -> Unit,
) {
    var presses by remember { mutableIntStateOf(0) }
    val inputError = remember { mutableStateOf(false) }
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    val inputState = remember { mutableStateOf(false) }
    var laporanSucces = remember { mutableStateOf(false) }

    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = { presses++ }) {
//                Icon(Icons.Default.Add, contentDescription = "Add")
//            }
//        }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lapor",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .size(width = 400.dp, height = 200.dp)
            ) {
                // make text field and button
                OutlinedTextField(
                    value = inputValue.value,
                    onValueChange = {
                        if (inputError.value) {
                            inputError.value = false
                        }
                        inputValue.value = it
                    },
                    isError = inputError.value,
                    maxLines = 8,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp)
                        .height(190.dp),
                    label = {
                        Text(text = "Enter your report here")
                    },
                )
                if (inputError.value) {
                    Text(text = "Required", color = Color.Red)
                }

            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = {
                    when {
                        inputValue.value.text.isEmpty() -> {
                            inputError.value = true
                        }
                        else -> {
                            inputError.value = false
                            inputState.value = true

                            viewModel.predict(inputValue.value.text)
                        }
                    }
                },
                content = {
                    Text(text = "Unggah Laporan", color = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            )

            if (inputState.value) {
                val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)

                LaunchedEffect(uiState) {
                    when (uiState) {
                        is UiState.Success -> {
                            laporanSucces.value = true
                            Log.d("LaporState", "Success")
//                    Toast.makeText(context, "Lapor Success", Toast.LENGTH_SHORT).show()
//                onNavigateHome()
                        }
                        is UiState.Loading -> {
                            Log.d("LaporState", "Loading try")
                            viewModel.predict(inputValue.value.text)
                        }

                        is UiState.Error -> {
                            Log.d("LaporState", "Error ${(uiState as UiState.Error).errorMessage}")
                            laporanSucces.value = true
                        }
                    }
                }
            }

            if (laporanSucces.value) {
                when (viewModel.labelItem.value?.label) {
                    "LABEL_0" -> {
                        // make alert dialog using compose
                        AlertDialog(
                            onDismissRequest = { inputState.value = false },
                            title = { Text(text = "Kamu Tidak Mengalami Pembullyan") },
                            text = {
                                Text(
                                    text = "Dari cerita yang kamu masukan tadi, kamu tidak terindikasi tindak bullying.\n" +
                                            "Untuk lebih yakin, Temukan masalah kamu pada halaman berikutnya!"
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        inputState.value = false
                                        navigateToDetail()
                                    },
                                    content = {
                                        Text(text = "OK", color = Color.White)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            top = 0.dp,
                                            bottom = 0.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(Color.Blue)
                                )
                            }
                        )
                    }
                    "LABEL_1" -> {
                        AlertDialog(
                            onDismissRequest = { inputState.value = false },
                            title = { Text(text = "Kamu Mengalami Pembullyan Kategori Sedang") },
                            text = {
                                Text(
                                    text = "Dari cerita yang kamu masukan tadi, kamu terindikasi tindak bullying.\n" +
                                            "Kamu bisa laporkan ke Pihak berwajib dan lakukan konseling dengan ahlinya. Untuk lebih yakin, Temukan masalah kamu pada halaman berikutnya!"
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        inputState.value = false
                                        navigateToDetail()
                                    },
                                    content = {
                                        Text(text = "OK", color = Color.White)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            top = 0.dp,
                                            bottom = 0.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(Color.Blue)
                                )
                            }
                        )
                    }
                    else -> {
                        AlertDialog(
                            onDismissRequest = { inputState.value = false },
                            title = { Text(text = "Kamu Mengalami Pembullyan Berat") },
                            text = {
                                Text(
                                    text = "Dari cerita yang kamu masukan tadi, kamu terindikasi tindak bullying.\n" +
                                            "Kamu bisa laporkan ke Pihak berwajib dan lakukan konseling dengan ahlinya. Untuk lebih yakin, Temukan masalah kamu pada halaman berikutnya!"
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        inputState.value = false
                                        navigateToDetail()
                                    },
                                    content = {
                                        Text(text = "OK", color = Color.White)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            top = 0.dp,
                                            bottom = 0.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(Color.Blue)
                                )
                            }
                        )
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun LaporScreenPreview() {
    LaporScreen()
}