package com.soyvictorherrera.nosedive.presentation.component.bottomSheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.presentation.component.button.MainButton
import com.soyvictorherrera.nosedive.presentation.component.item.RateFriendItem
import com.soyvictorherrera.nosedive.presentation.component.modifier.sheetPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

sealed class RecentlyRatedSheetEvent {
    data class RateFriend(val friend: FriendModel) : RecentlyRatedSheetEvent()
    object ViewAllFriends : RecentlyRatedSheetEvent()
}

@Composable
fun RecentlyRatedSheetContent(
    recentlyRatedFriends: List<FriendModel>,
    modifier: Modifier = Modifier,
    onSheetEvent: (event: RecentlyRatedSheetEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .sheetPadding()
    ) {

        Text(
            text = stringResource(R.string.sheet_recently_rated_title),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (recentlyRatedFriends.isEmpty()) Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.message_no_recently_rated_friend),
                style = MaterialTheme.typography.caption
            )
        } else LazyColumn(
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(recentlyRatedFriends) { friend ->
                RateFriendItem(
                    friend = friend,
                    onItemClick = {
                        onSheetEvent(
                            RecentlyRatedSheetEvent.RateFriend(friend)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        MainButton(
            text = stringResource(R.string.sheet_action_all_contacts),
            icon = Icons.Default.People,
            onClick = {
                onSheetEvent(
                    RecentlyRatedSheetEvent.ViewAllFriends
                )
            }
        )
    }
}

@Preview("With friend list")
@Composable
private fun RecentlyRatedSheetContentPreview() {
    NosediveTheme {
        RecentlyRatedSheetContent(
            recentlyRatedFriends = listOf(
                FriendModel(id = "", name = "Jessica Herrera"),
                FriendModel(id = "", name = "Irais Herrera"),
                FriendModel(id = "", name = "Antonio Martinez")
            )
        ) {}
    }
}

@Preview("With empty friend list")
@Composable
private fun RecentlyRatedSheetContentPreviewEmpty() {
    NosediveTheme {
        RecentlyRatedSheetContent(
            recentlyRatedFriends = emptyList()
        ) {}
    }
}
