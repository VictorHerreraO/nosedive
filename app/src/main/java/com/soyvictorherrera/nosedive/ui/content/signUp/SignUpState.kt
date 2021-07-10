package com.soyvictorherrera.nosedive.ui.content.signUp

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
}

sealed class SignUpError {
    object ErrorUnknown : SignUpError()
    object UnableToCreateAccount : SignUpError()
}
