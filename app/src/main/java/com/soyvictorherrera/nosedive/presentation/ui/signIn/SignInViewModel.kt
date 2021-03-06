package com.soyvictorherrera.nosedive.presentation.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.authentication.SignInUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState>
        get() = _signInState

    private val _signInError = MutableLiveData<Event<SignInError>>()
    val signInError: LiveData<Event<SignInError>>
        get() = _signInError

    fun signIn(email: String, password: String) {
        _signInState.value = SignInState.Loading

        viewModelScope.launch {
            signInUseCase(
                UserEntity(
                    email = email,
                    password = password
                )
            ).let { result ->
                _signInState.value = SignInState.Idle
                when (result) {
                    is Result.Success -> {
                        Timber.d("success: ${result.data}")
                        _navigateTo.value = Event(Screen.Home)
                    }
                    is Result.Error -> {
                        Timber.d(result.exception, "error: ")
                        _signInError.value = Event(SignInError.WrongCredentials)
                    }
                    else -> {
                        Timber.d("else")
                    }
                }
            }
        }
    }

    fun signUp() {
        _navigateTo.value = Event(Screen.SignUp)
    }

    fun resetPassword() {
        _signInError.value = Event(SignInError.NotImplemented)
    }

}
