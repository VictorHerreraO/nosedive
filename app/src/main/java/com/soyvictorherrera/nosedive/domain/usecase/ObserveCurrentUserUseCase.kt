package com.soyvictorherrera.nosedive.domain.usecase

import android.util.Log
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

class ObserveCurrentUserUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : BaseUseCase<Result<UserEntity>>() {

    override suspend fun buildFlow(): Flow<Result<UserEntity>> {
        return authRepository.getCurrentAuthentication()
            .map { authResult ->
                when (authResult) {
                    is Result.Success -> {
                        authResult.data.userId!!
                    }
                    is Result.Error -> {
                        throw authResult.exception
                    }
                    else -> {
                        throw IllegalStateException("authResult must be success or error")
                    }
                }
            }
            .flatMapMerge { userId ->
                userRepository.observeUser(userId).map { userResult ->
                    userResult.map { data ->
                        with(data) {
                            UserEntity(id, name, email, password, photoUrl)
                        }
                    }
                }
            }
            .catch { throwable ->
                Log.e(TAG, "flow catch -> ", throwable)
                emit(Result.Error(RuntimeException(throwable.cause)))
            }
    }

}
