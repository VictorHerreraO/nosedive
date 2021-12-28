package com.soyvictorherrera.nosedive.presentation.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.authentication.SignUpUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState>
        get() = _signUpState

    private val _signUpError = MutableLiveData<Event<SignUpError>>()
    val signUpError: LiveData<Event<SignUpError>>
        get() = _signUpError

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        _signUpState.value = SignUpState.Loading
        viewModelScope.launch {
            signUpUseCase(
                UserEntity(
                    name = name,
                    email = email,
                    password = password
                )
            ).let { result ->
                _signUpState.value = SignUpState.Idle
                when (result) {
                    is Result.Success -> {
                        Timber.d("success: ${result.data}")
                        _navigateTo.value = Event(Screen.Home)
                    }
                    is Result.Error -> {
                        Timber.e(result.exception, "error by: ")
                        _signUpError.value = Event(SignUpError.UnableToCreateAccount)
                    }
                    else -> {
                        Timber.d("else")
                        _signUpError.value = Event(SignUpError.ErrorUnknown)
                    }
                }
            }
        }
    }

    fun signIn() {
        _navigateTo.value = Event(Screen.SignIn)
    }

}
