package com.soyvictorherrera.nosedive.presentation.ui.friendList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(

) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _friendList = MutableLiveData<List<FriendModel>>()
    val friendList: LiveData<List<FriendModel>>
        get() = _friendList

    fun onFriendListChanged(friendList: List<FriendModel>) {
        _friendList.value = friendList
            .filter { !it.anonymous }
            .sortedBy { it.name }
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onFriendSelected(friend: FriendModel) {
        _navigateTo.value = Event(Screen.FriendProfile(
            userId = friend.id
        ))
    }

}
