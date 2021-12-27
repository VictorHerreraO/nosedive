package com.soyvictorherrera.nosedive.domain.usecase.user

import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObserveUserProfileUseCase(
    private val userRepository: UserRepository,
    private val userEntityMapper: DomainMapper<UserEntity, UserModel>
) : BaseUseCase<Result<UserModel>>() {

    var userId: String? = null

    override suspend fun buildFlow(): Flow<Result<UserModel>> {
        return userId.let { id ->
            if (id.isNullOrEmpty()) flowOf(
                Result.Error(
                    IllegalArgumentException("[userId] must not be null or empty")
                )
            ) else userRepository.observeUser(userId = id)
                .map {
                    it.map(userEntityMapper::toDomainModel)
                }
        }
    }

}
