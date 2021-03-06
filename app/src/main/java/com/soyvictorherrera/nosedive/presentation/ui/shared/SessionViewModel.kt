package com.soyvictorherrera.nosedive.presentation.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.presentation.ui.home.SessionState
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Current user session details
 *
 * Intended to be shared across fragments
 *
 * @author Víctor Herrera
 */
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val preferences: PreferenceUtil
) : ViewModel() {

    private val _state = MutableLiveData<SessionState>(SessionState.Checking)
    val state: LiveData<SessionState>
        get() = _state

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    private var observeCurrentUserJob: Job? = null

    init {
        observeSessionOpen()
    }

    private fun observeSessionOpen() {
        preferences.observeSessionOpen().onEach { isSessionOpen ->
            Timber.i("session open updated, value is [$isSessionOpen]")
            if (isSessionOpen) {
                observeCurrentUserJob = observeCurrentUser()
                _state.value = SessionState.SignedIn
            } else {
                observeCurrentUserJob?.cancel(CancellationException("[sessionOpen] was false"))
                _state.value = SessionState.SignedOut
            }
        }.launchIn(viewModelScope)
    }

    private fun observeCurrentUser(): Job = viewModelScope.launch {
        observeCurrentUserUseCase.execute { result ->
            when (result) {
                is Result.Error -> {
                    Timber.e(result.exception)
                    _state.value = SessionState.SignedOut
                }
                Result.Loading -> {
                    _state.value = SessionState.Checking
                }
                is Result.Success -> {
                    Timber.i("current user updated")
                    _user.value = result.data!!.also {
                        preferences.setUserId(it.id!!)
                    }
                }
            }
        }
    }

}
