package com.soyvictorherrera.nosedive.domain.usecase

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

class GetCurrentUserUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<UserEntity> {
        authRepository.getCurrentAuthentication().let { result ->
            return when (result) {
                is Result.Error -> {
                    result
                }
                is Result.Success -> {
                    result.data.userId?.let { uid ->
                        userRepository.getUser(uid)
                    } ?: Result.Error(RuntimeException("no user id on current auth"))
                }
                else -> throw IllegalStateException()
            }
        }
    }

}
