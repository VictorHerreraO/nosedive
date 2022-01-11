package com.soyvictorherrera.nosedive.presentation.ui.rateUser

sealed class RateUserState {
    object Loading: RateUserState()
    object Idle: RateUserState()
}
