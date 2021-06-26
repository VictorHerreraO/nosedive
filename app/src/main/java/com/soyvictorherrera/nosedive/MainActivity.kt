package com.soyvictorherrera.nosedive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    NosediveTheme {
        Surface(color = MaterialTheme.colors.background) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = MaterialTheme.colors.background,
                    elevation = 4.dp,
                    modifier = Modifier.size(162.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Image",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(122.dp)
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "4.20", style = MaterialTheme.typography.h3)
                    Text(text = "17", style = MaterialTheme.typography.h5)
                }

            }
        }
    }
}

@Preview
@Composable
fun MainActivityContentPreview() {
    MainActivityContent()
}
