package com.soyvictorherrera.nosedive.data.repository.user

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

interface UserRepository {

    suspend fun saveUser(user: UserEntity): Result<UserEntity>

    suspend fun getUser(userId: String): Result<UserEntity>

}
