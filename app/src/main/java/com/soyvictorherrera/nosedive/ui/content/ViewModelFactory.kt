package com.soyvictorherrera.nosedive.ui.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soyvictorherrera.nosedive.ui.content.signIn.SignInViewModel
import com.soyvictorherrera.nosedive.ui.content.signUp.SignUpViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SignInViewModel::class.java) -> {
                    SignInViewModel()
                }
                isAssignableFrom(SignUpViewModel::class.java) -> {
                    SignUpViewModel()
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        } as T
}
