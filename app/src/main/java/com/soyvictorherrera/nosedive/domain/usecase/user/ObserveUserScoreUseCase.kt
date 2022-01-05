package com.soyvictorherrera.nosedive.domain.usecase.user

import com.soyvictorherrera.nosedive.data.repository.userScore.UserScoreRepository
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.UserScoreModel
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@Deprecated("User ObserveUserStatsUseCase")
class ObserveUserScoreUseCase(
    private val scoreRepository: UserScoreRepository,
    private val scoreEntityMapper: DomainMapper<UserScoreEntity, UserScoreModel>
) : BaseUseCase<Result<UserScoreModel>>() {

    var userId: String? = null

    override suspend fun buildFlow(): Flow<Result<UserScoreModel>> {
        return userId.let { id ->
            if (id.isNullOrEmpty()) flowOf(
                Result.Error(IllegalArgumentException("[userId] must not be null or empty"))
            ) else scoreRepository
                .observeUserScore(userId = id)
                .map {
                    it.map(scoreEntityMapper::toDomainModel)
                }
        }
    }
}
