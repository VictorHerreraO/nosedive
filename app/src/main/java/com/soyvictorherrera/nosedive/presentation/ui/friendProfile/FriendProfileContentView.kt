package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.presentation.component.button.MainButton
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.profile.UserDetails
import com.soyvictorherrera.nosedive.presentation.component.profile.UserStats
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun FriendProfileContentView(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            NoTitleTopAppBar(onNavigateBack = { })
        },
        content = {
            FriendProfileContent(

            )
        },
        bottomBar = {
            FriendProfileBottomBar(

            )
        }
    )
}

@Composable
fun FriendProfileContent(modifier: Modifier = Modifier): Unit = Column(
    modifier = modifier.padding(horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Spacer(modifier = Modifier.height(16.dp))

    UserDetails(
        userName = "{user-name}"
    )

    Spacer(modifier = Modifier.height(48.dp))

    UserStats(
        followers = 0,
        ratings = 0,
        following = 0,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FriendProfileBottomBar(modifier: Modifier = Modifier): Unit = Column(
    modifier = modifier.padding(horizontal = 32.dp)
) {
    MainButton(
        text = "Calificar a {user-name}",
        onClick = { /*TODO*/ },
        icon = Icons.Sharp.Star
    )

    Spacer(modifier = Modifier.height(64.dp))
}

@Preview
@Composable
fun FriendProfileContentViewPreview() {
    NosediveTheme {
        FriendProfileContentView(

        )
    }
}
