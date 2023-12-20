package com.marqumil.bullience_dev.ui.screen.jelajah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.marqumil.bullience_dev.R

@Composable
fun JelajahScreen(
    modifier: Modifier = Modifier

) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Text(stringResource(R.string.menu_jelajah))
    }
}