package com.soyvictorherrera.nosedive.data.repository.userStats

import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface UserStatsRepository {

    fun observeUserStats(userId: String): Flow<Result<UserStatsEntity>>

}
