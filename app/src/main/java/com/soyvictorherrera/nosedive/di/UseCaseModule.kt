package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignInUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository
    ): SignInUseCase {
        return SignInUseCase(
            authRepository = authRepository,
            userRepository = userRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository
    ): SignUpUseCase {
        return SignUpUseCase(
            authRepository = authRepository,
            userRepository = userRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideObserveCurrentUserUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository
    ): ObserveCurrentUserUseCase {
        return ObserveCurrentUserUseCase(
            authRepository = authRepository,
            userRepository = userRepository
        )
    }

}
