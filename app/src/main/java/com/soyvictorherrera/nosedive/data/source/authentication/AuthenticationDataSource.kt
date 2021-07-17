package com.soyvictorherrera.nosedive.data.source.authentication

import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationDataSource {

    suspend fun signIn(request: AuthenticationEntity): Result<AuthenticationEntity>

    suspend fun signUp(request: AuthenticationEntity): Result<AuthenticationEntity>

    suspend fun getCurrentAuthentication(): Flow<Result<AuthenticationEntity>>

}
