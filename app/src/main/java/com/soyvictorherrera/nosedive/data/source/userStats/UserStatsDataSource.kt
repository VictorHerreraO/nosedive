package com.soyvictorherrera.nosedive.data.source.userStats

import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface UserStatsDataSource {

    fun observeUserStats(userId: String): Flow<Result<UserStatsEntity>>

}
