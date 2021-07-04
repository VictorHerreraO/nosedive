package com.soyvictorherrera.nosedive.ui.content.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.SignUpUseCase
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.util.Event

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        val result: Result<UserEntity> = signUpUseCase(
            UserEntity(
                name = name,
                email = email,
                password = password
            )
        )
        when (result) {
            is Result.Success -> {
                Log.d("signUp:", "success: ${result.data}")
            }
            is Result.Error -> {
                Log.e("signUp:", "error by: ", result.exception)
            }
            else -> {
                Log.d("signUp:", "else")
            }
        }
    }

    fun signIn() {
        _navigateTo.value = Event(Screen.SignIn)
    }

}
