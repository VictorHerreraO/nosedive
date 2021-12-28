package com.soyvictorherrera.nosedive.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _homeState = MutableLiveData<HomeState>(HomeState.Loading)
    val homeState: LiveData<HomeState>
        get() = _homeState

    private val _sessionState = MutableLiveData<SessionState>(SessionState.Checking)
    val sessionState: LiveData<SessionState>
        get() = _sessionState

    private val _homeError = MutableLiveData<Event<HomeError>>()
    val homeError: LiveData<Event<HomeError>>
        get() = _homeError

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    private val _bottomSheetEvent = MutableLiveData<Event<BottomSheetEvent>>()
    val bottomSheetEvent: LiveData<Event<BottomSheetEvent>>
        get() = _bottomSheetEvent

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        _user.value = result.data!!
                        _sessionState.value = SessionState.SignedIn
                        _homeState.value = HomeState.Idle
                    }
                    is Result.Error -> {
                        Timber.d(result.exception, "error by:")
                        _sessionState.value = SessionState.SignedOut
                    }
                    else -> {
                        Timber.d("else")
                    }
                }
            }
        }
    }

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
