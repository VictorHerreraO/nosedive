package com.soyvictorherrera.nosedive.data.source.authentication

import com.soyvictorherrera.nosedive.data.Result

interface AuthenticationDataSource {

    suspend fun signIn(request: AuthenticationEntity): Result<AuthenticationEntity>

    suspend fun signUp(request: AuthenticationEntity): Result<AuthenticationEntity>

}
