package com.soyvictorherrera.nosedive.data.source.user

import com.soyvictorherrera.nosedive.data.Result
import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    suspend fun saveUser(user: UserEntity): Result<UserEntity>

    suspend fun getUser(userId: String): Flow<Result<UserEntity>>

}
