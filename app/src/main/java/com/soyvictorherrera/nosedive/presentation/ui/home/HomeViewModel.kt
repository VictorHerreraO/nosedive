package com.soyvictorherrera.nosedive.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun viewProfile() {
        _navigateTo.value = Event(Screen.Profile)
    }

    fun addFriend() {
        _bottomSheetEvent.value = Event(BottomSheetEvent.ShowAddFriendBottomSheet)
    }

    fun codeShare() {
        _navigateTo.value = Event(Screen.CodeSharing)
    }

    fun codeScan() {
        _navigateTo.value = Event(Screen.CodeScanning)
    }

}
