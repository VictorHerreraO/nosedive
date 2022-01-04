package com.soyvictorherrera.nosedive.presentation.ui.home

import com.soyvictorherrera.nosedive.domain.model.FriendModel

sealed class BottomSheetType {
    object AddFriendSheet : BottomSheetType()
    data class RecentlyRatedFriendsSheet(val friendList: List<FriendModel>) : BottomSheetType()
}
