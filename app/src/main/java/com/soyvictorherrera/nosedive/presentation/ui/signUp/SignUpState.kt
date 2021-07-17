package com.soyvictorherrera.nosedive.presentation.ui.signUp

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
}

sealed class SignUpError {
    object ErrorUnknown : SignUpError()
    object UnableToCreateAccount : SignUpError()
}
