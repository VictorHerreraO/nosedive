package com.soyvictorherrera.nosedive.ui.content.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.material.icons.sharp.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.ui.composable.common.DefaultBottomAppBar
import com.soyvictorherrera.nosedive.ui.composable.profile.UserDetails
import com.soyvictorherrera.nosedive.ui.composable.profile.UserStats
import com.soyvictorherrera.nosedive.ui.theme.Forest_Green
import com.soyvictorherrera.nosedive.ui.theme.Forest_Green_07
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

        UserDetails(
            userName = userName,
            userPhotoBackgroundColor = Forest_Green_07
        )

        Spacer(modifier = Modifier.height(48.dp))

        UserStats(
            followers = 0,
            ratings = 2,
            following = 1,
            modifier = Modifier.fillMaxWidth()
        )

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
    DefaultBottomAppBar(
        onFriendsClick = { /*TODO*/ },
        onRateClick = { /*TODO*/ },
        onAddFriendClick = { /*TODO*/ }
    )
}

@Preview
@Composable
fun HomeContentPreviewDark() {
    NosediveTheme(darkTheme = true) {
        HomeContentView(userName = "Víctor Herrera")
    }
}
