package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PersonAdd
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.presentation.component.button.ActionButton
import com.soyvictorherrera.nosedive.presentation.component.button.MainButton
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.profile.UserDetails
import com.soyvictorherrera.nosedive.presentation.component.profile.UserStats
import com.soyvictorherrera.nosedive.presentation.extensions.getPhotoUri
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

sealed class FriendProfileEvent {
    object NavigateBack : FriendProfileEvent()
}

sealed class FriendProfileActionEvent {
    object RateUser : FriendProfileActionEvent()
    object FollowUser : FriendProfileActionEvent()
}

@Composable
@ExperimentalAnimationApi
fun FriendProfileContentView(
    user: UserModel,
    userStats: UserStatsModel,
    userScore: Double?,
    canFollowUser: Boolean,
    modifier: Modifier = Modifier,
    onNavigationEvent: (event: FriendProfileEvent) -> Unit,
    onActionEvent: (event: FriendProfileActionEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            NoTitleTopAppBar(onNavigateBack = {
                onNavigationEvent(FriendProfileEvent.NavigateBack)
            })
        },
        content = {
            FriendProfileContent(
                user = user,
                userStats = userStats,
                userScore = userScore
            )
        },
        bottomBar = {
            FriendProfileBottomBar(
                user = user,
                canFollowUser = canFollowUser,
                onActionEvent = onActionEvent
            )
        }
    )
}

@Composable
fun FriendProfileContent(
    user: UserModel,
    userStats: UserStatsModel,
    userScore: Double?,
    modifier: Modifier = Modifier
): Unit = Column(
    modifier = modifier.padding(horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Spacer(modifier = Modifier.height(16.dp))

    UserDetails(
        userName = user.name,
        userScore = userScore,
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
}

@Composable
@ExperimentalAnimationApi
fun FriendProfileBottomBar(
    user: UserModel,
    canFollowUser: Boolean,
    modifier: Modifier = Modifier,
    onActionEvent: (event: FriendProfileActionEvent) -> Unit
): Unit = Column(
    modifier = modifier.padding(horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    MainButton(
        text = stringResource(R.string.friend_profile_rate_user, user.name),
        onClick = { onActionEvent(FriendProfileActionEvent.RateUser) },
        icon = Icons.Sharp.Star
    )

    AnimatedVisibility(visible = canFollowUser) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))

            ActionButton(
                text = stringResource(R.string.friend_profile_follow_user),
                onClick = { onActionEvent(FriendProfileActionEvent.FollowUser) },
                icon = Icons.Sharp.PersonAdd
            )
        }
    }

    Spacer(modifier = Modifier.height(64.dp))
}

@Preview
@Composable
@ExperimentalAnimationApi
fun FriendProfileContentViewPreview() {
    NosediveTheme {
        FriendProfileContentView(
            user = UserModel(
                name = "Jessica Herrera",
                email = "",
                score = 4.3127
            ),
            userStats = UserStatsModel(
                followers = 17,
                ratings = 20,
                following = 11
            ),
            userScore = 4.3127,
            canFollowUser = true,
            onNavigationEvent = { },
            onActionEvent = { }
        )
    }
}
