package com.soyvictorherrera.nosedive.presentation.ui.friendList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.extensions.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendListViewModel @Inject constructor(

) : ViewModel() {

    private val _friendList = MutableLiveData<List<UserModel>>()
    val friendList: LiveData<List<UserModel>>
        get() = _friendList

    fun onFriendListChanged(friendList: List<FriendModel>) {
        _friendList.value = friendList.asSequence()
            .map { it.toUserModel() }
            .sortedBy { it.name }
            .toList()
    }

}
