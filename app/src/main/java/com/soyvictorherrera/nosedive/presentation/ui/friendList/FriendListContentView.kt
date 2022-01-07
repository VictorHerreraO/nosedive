package com.soyvictorherrera.nosedive.presentation.ui.friendList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.presentation.component.common.ProminentTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.item.FriendListItem
import com.soyvictorherrera.nosedive.presentation.component.modifier.listPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

sealed class FriendListEvent {
    object NavigateBack : FriendListEvent()
    object SearchFriend : FriendListEvent()
    data class FriendSelected(val friend: FriendModel) : FriendListEvent()
}

@Composable
@ExperimentalMaterialApi
fun FriendListContentView(
    friendList: List<FriendModel>,
    modifier: Modifier = Modifier,
    onFriendListEvent: (event: FriendListEvent) -> Unit
): Unit = Scaffold(
    modifier = modifier,
    topBar = {
        FriendListTopAppBar(
            onNavigateBack = {
                onFriendListEvent(FriendListEvent.NavigateBack)
            },
            onSearch = {
                onFriendListEvent(FriendListEvent.SearchFriend)
            }
        )
    },
    content = {
        FriendListContent(
            userList = friendList,
            onUserClick = {
                onFriendListEvent(FriendListEvent.FriendSelected(it))
            }
        )
    }
)

@Composable
fun FriendListTopAppBar(
    onNavigateBack: () -> Unit,
    onSearch: () -> Unit,
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
        IconButton(onClick = { onNavigateBack() }) {
            Icon(
                imageVector = Icons.Sharp.ArrowBack,
                contentDescription = null
            )
        }
    },
    actions = {
        IconButton(onClick = { onSearch() }) {
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
@ExperimentalMaterialApi
fun FriendListContent(
    userList: List<FriendModel>,
    onUserClick: (user: FriendModel) -> Unit,
    modifier: Modifier = Modifier
) {
    if (userList.isEmpty()) Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.friend_list_empty),
            style = MaterialTheme.typography.caption
        )
    } else LazyColumn(
        modifier = modifier.listPadding(),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(userList) { user ->
            FriendListItem(
                user = user,
                onItemClick = { onUserClick(user) }
            )
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun FriendListContentViewPreview() {
    NosediveTheme {
        FriendListContentView(
            friendList = listOf(
                FriendModel(
                    id = "",
                    name = "Jessica Herrera"
                )
            )
        ) {}
    }
}

@Preview("Empty list")
@Composable
@ExperimentalMaterialApi
fun FriendListContentViewPreviewEmpty() {
    NosediveTheme {
        FriendListContentView(
            friendList = emptyList()
        ) {}
    }
}
