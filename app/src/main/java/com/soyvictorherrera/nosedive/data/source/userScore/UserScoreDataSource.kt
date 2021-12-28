package com.soyvictorherrera.nosedive.data.source.userScore

import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface UserScoreDataSource {

    fun observeUserScore(userId: String): Flow<Result<UserScoreEntity>>

}
