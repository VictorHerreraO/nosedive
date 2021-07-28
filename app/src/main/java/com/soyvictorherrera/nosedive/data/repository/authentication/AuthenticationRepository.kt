package com.soyvictorherrera.nosedive.data.repository.authentication

import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun signIn(email: String, password: String): Result<AuthenticationEntity>

    suspend fun signUp(email: String, password: String): Result<AuthenticationEntity>

    suspend fun getCurrentAuthentication(): Flow<Result<AuthenticationEntity>>

    suspend fun updateUserPassword(password: String, newPassword: String): Flow<Result<Boolean>>

}
