package com.soyvictorherrera.nosedive.domain.usecase.user

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class UpdateUserPasswordUseCase(
    private val authRepository: AuthenticationRepository
) : BaseUseCase<Result<Boolean>>() {

    var password = ""
    var newPassword = ""

    override suspend fun buildFlow(): Flow<Result<Boolean>> {
        return authRepository.updateUserPassword(
            password = password,
            newPassword = newPassword
        ).catch { emit(Result.Error(RuntimeException("unable to update user password", it))) }
    }

}
