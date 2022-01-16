package com.soyvictorherrera.nosedive.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _homeState = MutableLiveData<HomeState>(HomeState.Loading)
    val homeState: LiveData<HomeState>
        get() = _homeState

    private val _homeError = MutableLiveData<Event<HomeError>>()
    val homeError: LiveData<Event<HomeError>>
        get() = _homeError

    private val _bottomSheetEvent = MutableLiveData<Event<BottomSheetEvent>>()
    val bottomSheetEvent: LiveData<Event<BottomSheetEvent>>
        get() = _bottomSheetEvent

    private var _recentlyRatedFriends = emptyList<FriendModel>()
    val recentlyRatedFriends: List<FriendModel>
        get() = _recentlyRatedFriends

    fun viewProfile() {
        _navigateTo.value = Event(Screen.Profile)
    }

    fun addFriend() {
        _bottomSheetEvent.value = Event(BottomSheetEvent.ShowAddFriendBottomSheet)
    }

    fun rateFriend() {
        _bottomSheetEvent.value = Event(BottomSheetEvent.ShowRateFriendBottomSheet)
    }

    fun codeShare() {
        hideBottomSheetAndNavigateTo(Screen.CodeSharing)
    }

    fun codeScan() {
        hideBottomSheetAndNavigateTo(Screen.CodeScanning)

    }

    fun viewFriendList() {
        hideBottomSheetAndNavigateTo(Screen.FriendList)
    }

    fun rateFriend(userId: String) {
        hideBottomSheetAndNavigateTo(
            Screen.RateUser(
                userId = userId
            )
        )
    }

    fun onUserChanged(user: UserModel) {
        _homeState.value = HomeState.Idle
    }

    fun onFriendListChange(friendList: List<FriendModel>) {
        _recentlyRatedFriends = friendList.asSequence()
            .filter { it.lastRatedParsed != null }
            .filter { !it.anonymous }
            .sortedBy { it.lastRatedParsed }
            .take(3)
            .toList()
    }

    fun viewNotifications() {
        _navigateTo.value = Event(Screen.Notification)
    }

    /**
     * Hide the bottom sheet and waits 500 ms to navigate to the destination
     *
     * The delay is required so the hidden state of the sheet gets saved
     */
    private fun hideBottomSheetAndNavigateTo(destination: Screen) {
        _bottomSheetEvent.value = Event(BottomSheetEvent.HideBottomSheet)
        viewModelScope.launch {
            delay(500)
            _navigateTo.value = Event(destination)
        }
    }

}
