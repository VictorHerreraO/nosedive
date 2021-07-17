package com.soyvictorherrera.nosedive.data.repository.user

import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUser(user: UserEntity): Result<UserEntity>

    suspend fun observeUser(userId: String): Flow<Result<UserEntity>>

}
