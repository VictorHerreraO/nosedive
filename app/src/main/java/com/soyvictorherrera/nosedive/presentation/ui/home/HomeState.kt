package com.soyvictorherrera.nosedive.presentation.ui.home

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
}

sealed class HomeError {

}

sealed class SessionState {
    object SignedIn : SessionState()
    object SignedOut : SessionState()
    object Checking : SessionState()
}

sealed class BottomSheetEvent {
    object ShowAddFriendBottomSheet : BottomSheetEvent()
    object ShowRateFriendBottomSheet : BottomSheetEvent()
    object HideBottomSheet : BottomSheetEvent()
}
