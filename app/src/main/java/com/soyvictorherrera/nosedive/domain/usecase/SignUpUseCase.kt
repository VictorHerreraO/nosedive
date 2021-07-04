package com.soyvictorherrera.nosedive.domain.usecase

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

class SignUpUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(user: UserEntity): Result<UserEntity> {
        return userRepository.signUpUser(user)
    }

}
