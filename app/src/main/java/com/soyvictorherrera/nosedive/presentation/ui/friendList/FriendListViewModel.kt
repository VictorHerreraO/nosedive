package com.soyvictorherrera.nosedive.presentation.ui.friendList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(

) : ViewModel() {

    private val _friendList = MutableLiveData<List<FriendModel>>()
    val friendList: LiveData<List<FriendModel>>
        get() = _friendList

    fun onFriendListChanged(friendList: List<FriendModel>) {
        _friendList.value = friendList.sortedBy { it.name }
    }

}
