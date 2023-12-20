package com.marqumil.bullience_dev.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marqumil.bullience_dev.R
import com.marqumil.bullience_dev.ui.theme.BannerColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToLapor: () -> Unit
) {
    HomeContent(
        modifier = modifier,
        onBackClick = navigateBack,
        onNavigateLapor = navigateToLapor,
    )

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNavigateLapor: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
        ){
            // rounded image and text beside it
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "user photo"
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
                    .background(color = Color.White),
                text = stringResource(id = R.string.Hallo) + " " + stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
        }
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp, 200.dp)
                .background(
                    color = BannerColor,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(0.dp),
        ){
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.bg_banner),
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
                contentDescription = "banner"
            )
            Column {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(16.dp),
                    text = stringResource(id = R.string.banner_text1),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(16.dp),
                    text = stringResource(id = R.string.banner_text2),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.padding(5.dp))

                // create button
                Button(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        // move to lapor screen when button clicked
                        onNavigateLapor()
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.menu_lapor),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp),
            text = stringResource(id = R.string.home_news),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
        )
    }
}
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateBack = {},
        navigateToLapor = {},
    )
}