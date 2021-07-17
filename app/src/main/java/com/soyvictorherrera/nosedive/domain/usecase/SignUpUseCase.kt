package com.soyvictorherrera.nosedive.domain.usecase

import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

class SignUpUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(user: UserEntity): Result<UserEntity> {
        val email = user.email ?: return Result.Error(
            RuntimeException("{email} must not be null")
        )
        val pwd = user.password ?: return Result.Error(
            RuntimeException("{password} must not be null")
        )
        val signUpResult = authRepository.signUp(
            email = email,
            password = pwd
        )

        if (signUpResult is Result.Error) return signUpResult
        if (signUpResult is Result.Loading) throw UnsupportedOperationException()

        val data = (signUpResult as Result.Success).data
        val safeUser = user.copy(
            id = data.userId,
            password = null
        )

        return userRepository.saveUser(safeUser)
    }

}
