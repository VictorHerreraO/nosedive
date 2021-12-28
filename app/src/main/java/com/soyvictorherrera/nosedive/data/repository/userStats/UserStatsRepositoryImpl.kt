package com.soyvictorherrera.nosedive.data.repository.userStats

import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsDataSource
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

class UserStatsRepositoryImpl(
    private val userStatsDataSource: UserStatsDataSource
) : UserStatsRepository {

    override fun observeUserStats(userId: String): Flow<Result<UserStatsEntity>> {
        return userStatsDataSource.observeUserStats(userId = userId)
    }

}
