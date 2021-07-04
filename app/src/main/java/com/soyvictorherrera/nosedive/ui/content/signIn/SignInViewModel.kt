package com.soyvictorherrera.nosedive.ui.content.signIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.SignInUseCase
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.util.Event

class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun signIn(email: String, password: String) {
        val result = signInUseCase(
            UserEntity(
                name = "",
                email = email,
                password = password
            )
        )

        when (result) {
            is Result.Success -> {
                Log.d("signIn", "success: ${result.data}")
            }
            is Result.Error -> {
                Log.e("signIn", "error by: ", result.exception)
            }
            else -> {
                Log.d("signIn", "else")
            }
        }
    }

    fun signUp() {
        _navigateTo.value = Event(Screen.SignUp)
    }

    fun resetPassword() {
        // TODO: 30/06/2021 navigate reset password
    }

}
