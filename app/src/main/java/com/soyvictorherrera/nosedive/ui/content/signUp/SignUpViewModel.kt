package com.soyvictorherrera.nosedive.ui.content.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.util.Event

class SignUpViewModel : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        Log.d("signUp", "ToDo")
    }

    fun signIn() {
        _navigateTo.value = Event(Screen.SignIn)
    }

}
