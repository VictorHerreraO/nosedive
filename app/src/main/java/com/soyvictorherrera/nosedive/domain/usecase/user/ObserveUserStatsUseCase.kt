package com.soyvictorherrera.nosedive.domain.usecase.user

import com.soyvictorherrera.nosedive.data.repository.userStats.UserStatsRepository
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObserveUserStatsUseCase(
    private val statsRepository: UserStatsRepository,
    private val statsEntityMapper: DomainMapper<UserStatsEntity, UserStatsModel>
) : BaseUseCase<Result<UserStatsModel>>() {

    var userId: String? = null

    override suspend fun buildFlow(): Flow<Result<UserStatsModel>> {
        return userId.let { id ->
            if (id.isNullOrEmpty()) flowOf(
                Result.Error(IllegalArgumentException("[userId] must not be null or empty"))
            ) else statsRepository
                .observeUserStats(userId = id)
                .map {
                    it.map(statsEntityMapper::toDomainModel)
                }

        }
    }

}
