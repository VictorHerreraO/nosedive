package com.soyvictorherrera.nosedive.data.repository.userScore

import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreDataSource
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

class UserScoreRepositoryImpl(
    private val userScoreDataSource: UserScoreDataSource
) : UserScoreRepository {

    override fun observeUserScore(userId: String): Flow<Result<UserScoreEntity>> {
        return userScoreDataSource.observeUserScore(userId = userId)
    }

}

