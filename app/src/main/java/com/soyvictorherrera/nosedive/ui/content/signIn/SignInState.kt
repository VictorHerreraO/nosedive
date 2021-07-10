package com.soyvictorherrera.nosedive.ui.content.signIn

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    data class Error(val error: SignInError) : SignInState()
}

sealed class SignInError {
    object ErrorUnknown : SignInError()
    object WrongCredentials : SignInError()
}
