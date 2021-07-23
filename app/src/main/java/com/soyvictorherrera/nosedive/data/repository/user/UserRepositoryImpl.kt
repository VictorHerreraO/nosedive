package com.soyvictorherrera.nosedive.data.repository.user

import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.net.URI

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun saveUser(user: UserEntity): Result<UserEntity> {
        return userDataSource.saveUser(user)
    }

    override suspend fun observeUser(userId: String): Flow<Result<UserEntity>> {
        return userDataSource.observeUser(userId)
    }

    override suspend fun updateUserPhoto(userId: String, photo: File): Flow<Result<URI>> {
        return userDataSource.updateUserPhoto(userId, photo)
    }
}
