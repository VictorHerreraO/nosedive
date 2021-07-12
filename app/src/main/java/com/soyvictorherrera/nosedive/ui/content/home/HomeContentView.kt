package com.soyvictorherrera.nosedive.ui.content.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.composable.profile.UserPhoto
import com.soyvictorherrera.nosedive.ui.theme.Forest_Green
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme

@Composable
fun HomeContentView(
    userName: String,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopBar(
                onUserClick = { /* TODO: 10/07/2021 */ },
                onNotificationsClick = { /* TODO: 10/07/2021 */ }
            )
        },
        content = {
            HomeContent(userName = userName)
        },
        bottomBar = {
            HomeBottomBar()
        }
    )
}

@Composable
fun HomeTopBar(
    onUserClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onUserClick) {
                Icon(
                    imageVector = Icons.Sharp.AccountCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onNotificationsClick) {
                Icon(
                    imageVector = Icons.Sharp.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 0.dp,
    )
}

@Composable
fun HomeContent(userName: String) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            UserPhoto(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier.weight(2f)
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "4.30", style = MaterialTheme.typography.h4)
                Text(text = "79", style = MaterialTheme.typography.subtitle1)
            }
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = userName,
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "0",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Seguidores",
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "2",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Calificaciones",
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "1",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Seguidos",
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(
                    state = rememberScrollState(),
                    orientation = Orientation.Vertical
                )
        ) {
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        imageVector = Icons.Sharp.TrendingUp,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        colorFilter = ColorFilter.tint(color = Forest_Green)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Tu calificación está oculta",
                            fontSize = 18.sp
                        )
                        Text(
                            "Necesitas al menos 10 calificaciones antes de poder ver tu calificación promedio",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun HomeBottomBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.surface),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {},
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Sharp.People,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Amigos", fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = {},
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Sharp.Star,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Calificar", fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = {},
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Sharp.PersonAdd,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Agregar", fontSize = 10.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeContentPreviewDark() {
    NosediveTheme(darkTheme = true) {
        HomeContentView(userName = "Víctor Herrera")
    }
}
