package com.soyvictorherrera.nosedive.data.repository.authentication

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun signIn(email: String, password: String): Result<AuthenticationEntity>

    suspend fun signUp(email: String, password: String): Result<AuthenticationEntity>

    suspend fun getCurrentAuthentication(): Flow<Result<AuthenticationEntity>>

}
