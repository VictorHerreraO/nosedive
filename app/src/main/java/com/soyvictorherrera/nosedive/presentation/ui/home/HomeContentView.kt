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
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.presentation.component.bottomSheet.AddContactSheetContent
import com.soyvictorherrera.nosedive.presentation.component.card.NewAccountAlertCard
import com.soyvictorherrera.nosedive.presentation.component.common.DefaultBottomAppBar
import com.soyvictorherrera.nosedive.presentation.component.profile.UserDetails
import com.soyvictorherrera.nosedive.presentation.component.profile.UserStats
import com.soyvictorherrera.nosedive.presentation.extensions.getPhotoUri
import com.soyvictorherrera.nosedive.presentation.theme.Black_32
import com.soyvictorherrera.nosedive.presentation.theme.Forest_Green_07
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.theme.modalBottomSheetShape

sealed class HomeEvent {
    object ViewProfile : HomeEvent()
    object ViewNotifications : HomeEvent()
    object ViewFriends : HomeEvent()
    object NewRate : HomeEvent()
    object AddFriend: HomeEvent()
    object CodeScan : HomeEvent()
    object CodeShare : HomeEvent()
}

@Composable
@ExperimentalMaterialApi
fun HomeContentView(
    user: UserModel,
    userStats: UserStatsModel,
    sheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigationEvent: (HomeEvent) -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = modalBottomSheetShape,
        sheetContent = {
            AddContactSheetContent(
                onScanCodeSelected = { onNavigationEvent(HomeEvent.CodeScan) },
                onShowCodeSelected = { onNavigationEvent(HomeEvent.CodeShare) }
            )
        },
        scrimColor = Black_32
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
                HomeContent(
                    user = user,
                    userStats = userStats
                )
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
fun HomeContent(
    user: UserModel,
    userStats: UserStatsModel
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        UserDetails(
            userName = user.name,
            userPhotoBackgroundColor = Forest_Green_07,
            userPhotoPainter = user.getPhotoUri()?.let { uri ->
                rememberImagePainter(uri)
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        UserStats(
            followers = userStats.followers,
            ratings = userStats.ratings,
            following = userStats.following,
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
@ExperimentalMaterialApi
fun HomeContentPreviewDark() {
    NosediveTheme(darkTheme = true) {
        HomeContentView(
            user = UserModel(
                name = "Víctor Herrera",
                email = ""
            ),
            userStats = UserStatsModel(),
            sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        ) {}
    }
}
