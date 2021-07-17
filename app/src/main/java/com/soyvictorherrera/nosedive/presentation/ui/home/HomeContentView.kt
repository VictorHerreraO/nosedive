package com.soyvictorherrera.nosedive.presentation.ui.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.presentation.componen.card.NewAccountAlertCard
import com.soyvictorherrera.nosedive.presentation.componen.common.DefaultBottomAppBar
import com.soyvictorherrera.nosedive.presentation.componen.profile.UserDetails
import com.soyvictorherrera.nosedive.presentation.componen.profile.UserStats
import com.soyvictorherrera.nosedive.presentation.theme.Forest_Green_07
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

sealed class HomeEvent {
    object ViewProfile : HomeEvent()
    object ViewNotifications : HomeEvent()
    object ViewFriends : HomeEvent()
    object NewRate : HomeEvent()
    object AddFriend : HomeEvent()
}

@Composable
fun HomeContentView(
    userName: String,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigationEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopBar(
                onUserClick = { onNavigationEvent(HomeEvent.ViewProfile) },
                onNotificationsClick = { onNavigationEvent(HomeEvent.ViewNotifications) }
            )
        },
        content = {
            HomeContent(userName = userName)
        },
        bottomBar = {
            HomeBottomBar(
                onFriendsClick = { onNavigationEvent(HomeEvent.ViewFriends) },
                onRateClick = { onNavigationEvent(HomeEvent.NewRate) },
                onAddFriendClick = { onNavigationEvent(HomeEvent.AddFriend) }
            )
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
            NewAccountAlertCard()
        }
    }
}

@Composable
fun HomeBottomBar(
    onFriendsClick: () -> Unit,
    onRateClick: () -> Unit,
    onAddFriendClick: () -> Unit
) {
    DefaultBottomAppBar(
        onFriendsClick = onFriendsClick,
        onRateClick = onRateClick,
        onAddFriendClick = onAddFriendClick
    )
}

@Preview
@Composable
fun HomeContentPreviewDark() {
    NosediveTheme(darkTheme = true) {
        HomeContentView(userName = "Víctor Herrera") {}
    }
}
