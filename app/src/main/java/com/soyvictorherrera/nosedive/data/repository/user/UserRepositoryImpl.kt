package com.soyvictorherrera.nosedive.data.repository.user

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun saveUser(user: UserEntity): Result<UserEntity> {
        return userDataSource.saveUser(user)
    }

    override suspend fun getUser(userId: String): Result<UserEntity> {
        return userDataSource.getUser(userId)
    }

}
