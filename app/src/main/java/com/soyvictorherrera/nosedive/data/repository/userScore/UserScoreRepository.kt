package com.soyvictorherrera.nosedive.data.repository.userScore

import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface UserScoreRepository {

    fun observeUserScore(userId: String): Flow<Result<UserScoreEntity>>

}
