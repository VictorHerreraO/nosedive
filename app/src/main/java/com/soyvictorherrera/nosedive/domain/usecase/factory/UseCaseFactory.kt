package com.soyvictorherrera.nosedive.domain.usecase.factory

import com.soyvictorherrera.nosedive.data.repository.factory.RepositoryFactory
import com.soyvictorherrera.nosedive.domain.usecase.SignInUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignUpUseCase

object UseCaseFactory {

    fun getSignInUseCase(): SignInUseCase {
        return SignInUseCase(RepositoryFactory.getUserRepository())
    }

    fun getSignUpUseCase(): SignUpUseCase {
        return SignUpUseCase(RepositoryFactory.getUserRepository())
    }

}
