package com.soyvictorherrera.nosedive.ui.content.home

sealed class HomeState {
    object Idle: HomeState()
    object Loading: HomeState()
}

sealed class HomeError {

}

sealed class SessionState {
    object SignedIn: SessionState()
    object SignedOut: SessionState()
    object Checking: SessionState()
}