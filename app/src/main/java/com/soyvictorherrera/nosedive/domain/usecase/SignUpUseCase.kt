package com.soyvictorherrera.nosedive.domain.usecase

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

class SignUpUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(user: UserEntity): Result<AuthenticationEntity> {
        val email = user.email ?: return Result.Error(
            RuntimeException("{email} must not be null")
        )
        val pwd = user.password ?: return Result.Error(
            RuntimeException("{password} must not be null")
        )
        return authRepository.signUp(
            email = email,
            password = pwd
        )
    }

}
