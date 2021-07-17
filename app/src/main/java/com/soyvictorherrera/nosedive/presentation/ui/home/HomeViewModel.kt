package com.soyvictorherrera.nosedive.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    private val _homeState = MutableLiveData<HomeState>(HomeState.Loading)
    val homeState: LiveData<HomeState>
        get() = _homeState

    private val _sessionState = MutableLiveData<SessionState>(SessionState.Checking)
    val sessionState: LiveData<SessionState>
        get() = _sessionState

    private val _homeError = MutableLiveData<Event<HomeError>>()
    val homeError: LiveData<Event<HomeError>>
        get() = _homeError

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        _user.value = result.data
                        _sessionState.value = SessionState.SignedIn
                        _homeState.value = HomeState.Idle
                    }
                    is Result.Error -> {
                        Log.d("getCurrentUser", "error by:", result.exception)
                        _sessionState.value = SessionState.SignedOut
                    }
                    else -> {
                        Log.d("getCurrentUser", "else")
                    }
                }
            }
        }
    }

}
