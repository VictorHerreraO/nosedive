package com.soyvictorherrera.nosedive.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soyvictorherrera.nosedive.domain.usecase.factory.UseCaseFactory
import com.soyvictorherrera.nosedive.presentation.ui.home.HomeViewModel
import com.soyvictorherrera.nosedive.presentation.ui.signIn.SignInViewModel
import com.soyvictorherrera.nosedive.presentation.ui.signUp.SignUpViewModel

@Suppress("UNCHECKED_CAST")
@Deprecated("Use @HiltViewModel")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SignInViewModel::class.java) -> {
                    SignInViewModel(UseCaseFactory.getSignInUseCase())
                }
                isAssignableFrom(SignUpViewModel::class.java) -> {
                    SignUpViewModel(UseCaseFactory.getSignUpUseCase())
                }
                isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(
                        UseCaseFactory.getGetCurrentUserUseCase()
                    )
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        } as T
}
