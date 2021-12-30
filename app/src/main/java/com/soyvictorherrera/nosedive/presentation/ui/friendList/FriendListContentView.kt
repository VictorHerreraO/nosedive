package com.soyvictorherrera.nosedive.presentation.ui.friendList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.card.FriendInfoCard
import com.soyvictorherrera.nosedive.presentation.component.common.ProminentTopAppBar
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun FriendListContentView(
    userList: List<UserModel>,
    modifier: Modifier = Modifier
): Unit = Scaffold(
    modifier = modifier,
    topBar = {
        FriendListTopAppBar(
            onNavigationEvent = { /*TODO*/ },
            onActionEvent = { /*TODO*/ }
        )
    },
    content = {
        FriendListContent(userList = userList)
    }
)

@Composable
fun FriendListTopAppBar(
    onNavigationEvent: () -> Unit,
    onActionEvent: () -> Unit,
    modifier: Modifier = Modifier
): Unit = ProminentTopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = stringResource(R.string.friend_list_title),
            style = MaterialTheme.typography.h5
        )
    },
    navigationIcon = {
        IconButton(onClick = { onNavigationEvent() }) {
            Icon(
                imageVector = Icons.Sharp.ArrowBack,
                contentDescription = null
            )
        }
    },
    actions = {
        IconButton(onClick = { onActionEvent() }) {
            Icon(
                imageVector = Icons.Sharp.Search,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        }
    },
    elevation = 0.dp
)

@Composable
fun FriendListContent(
    userList: List<UserModel>,
    modifier: Modifier = Modifier
) = LazyColumn(
    modifier = modifier.padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(1.dp)
) {
    items(userList) { user ->
        FriendInfoCard(
            user = user,
            onItemClick = { }
        )
    }
}

@Preview
@Composable
fun FriendListContentViewPreview() {
    NosediveTheme {
        FriendListContentView(
            userList = listOf(
                UserModel(
                    name = "Jessica Herrera",
                    email = ""
                )
            )
        )
    }
}
