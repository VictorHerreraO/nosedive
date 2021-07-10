package com.soyvictorherrera.nosedive.ui.content.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.SignUpUseCase
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.util.Event
import kotlinx.coroutines.launch

class SignUpViewModel(
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
                        Log.d("signUp:", "success: ${result.data}")
                        _navigateTo.value = Event(Screen.Home)
                    }
                    is Result.Error -> {
                        Log.e("signUp:", "error by: ", result.exception)
                        _signUpError.value = Event(SignUpError.UnableToCreateAccount)
                    }
                    else -> {
                        Log.d("signUp:", "else")
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
