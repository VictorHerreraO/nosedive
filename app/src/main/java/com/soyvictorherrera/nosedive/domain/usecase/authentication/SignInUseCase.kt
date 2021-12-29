package com.soyvictorherrera.nosedive.domain.usecase.authentication

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result

class SignInUseCase(
    private val authRepository: AuthenticationRepository,
    private val preferences: PreferenceUtil
) {

    suspend operator fun invoke(user: UserEntity): Result<AuthenticationEntity> {
        val email = user.email ?: return Result.Error(
            RuntimeException("{email} must not be null")
        )
        val pwd = user.password ?: return Result.Error(
            RuntimeException("{password} must not be null")
        )
        return authRepository.signIn(
            email = email,
            password = pwd
        ).also {
            preferences.setSessionOpen(it is Result.Success)
        }
    }

}
