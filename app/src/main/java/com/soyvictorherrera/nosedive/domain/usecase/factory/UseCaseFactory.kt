package com.soyvictorherrera.nosedive.domain.usecase.factory

import com.soyvictorherrera.nosedive.data.repository.factory.RepositoryFactory
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignInUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignUpUseCase

object UseCaseFactory {

    fun getSignInUseCase(): SignInUseCase {
        return SignInUseCase(
            authRepository = RepositoryFactory.getAuthenticationRepository(),
            userRepository = RepositoryFactory.getUserRepository()
        )
    }

    fun getSignUpUseCase(): SignUpUseCase {
        return SignUpUseCase(
            authRepository = RepositoryFactory.getAuthenticationRepository(),
            userRepository = RepositoryFactory.getUserRepository()
        )
    }

    fun getGetCurrentUserUseCase(): ObserveCurrentUserUseCase {
        return ObserveCurrentUserUseCase(
            authRepository = RepositoryFactory.getAuthenticationRepository(),
            userRepository = RepositoryFactory.getUserRepository()
        )
    }

}
