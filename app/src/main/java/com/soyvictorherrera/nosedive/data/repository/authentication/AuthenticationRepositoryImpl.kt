package com.soyvictorherrera.nosedive.data.repository.authentication

import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val authDataSource: AuthenticationDataSource
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String): Result<AuthenticationEntity> {
        return authDataSource.signIn(
            AuthenticationEntity(
                email = email.lowercase().trim(),
                password = password.trim()
            )
        )
    }

    override suspend fun signUp(email: String, password: String): Result<AuthenticationEntity> {
        return authDataSource.signUp(
            AuthenticationEntity(
                email = email.lowercase().trim(),
                password = password.trim()
            )
        )
    }

    override suspend fun getCurrentAuthentication(): Flow<Result<AuthenticationEntity>> {
        return authDataSource.getCurrentAuthentication()
    }
}
