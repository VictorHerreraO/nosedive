package com.soyvictorherrera.nosedive.ui.content.signIn

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.util.Event

class SignInViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun signIn(email: String, password: String) {
        // TODO: 30/06/2021 login
        Log.d("sigIn", "ToDo")
    }

    fun signUp() {
        _navigateTo.value = Event(Screen.SignUp)
    }

    fun resetPassword() {
        // TODO: 30/06/2021 navigate reset password
    }

}
