package com.soyvictorherrera.nosedive.data.source.user

import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.net.URI

interface UserDataSource {

    suspend fun saveUser(user: UserEntity): Result<UserEntity>

    suspend fun observeUser(userId: String): Flow<Result<UserEntity>>

    suspend fun getUser(userId: String): Result<UserEntity>

    suspend fun updateUserPhoto(userId: String, photo: File): Flow<Result<URI>>

}
