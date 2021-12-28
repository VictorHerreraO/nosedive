package com.soyvictorherrera.nosedive.domain.usecase

import android.util.Log
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import timber.log.Timber

class ObserveCurrentUserUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val userEntityMapper: DomainMapper<UserEntity, UserModel>
) : BaseUseCase<Result<UserModel>>() {

    @FlowPreview
    override suspend fun buildFlow(): Flow<Result<UserModel>> {
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
                userRepository.observeUser(userId).map { result ->
                    result.map { userEntityMapper.toDomainModel(it) }
                }
            }
            .catch { throwable ->
                Timber.e(throwable, "flow catch -> ")
                emit(Result.Error(RuntimeException(throwable.cause)))
            }
    }

}
