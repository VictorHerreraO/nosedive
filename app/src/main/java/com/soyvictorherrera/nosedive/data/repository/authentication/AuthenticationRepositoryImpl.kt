package com.soyvictorherrera.nosedive.data.repository.authentication

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity

class AuthenticationRepositoryImpl(
    private val authSource: AuthenticationDataSource
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String): Result<AuthenticationEntity> {
        return authSource.signIn(
            AuthenticationEntity(
                email = email.lowercase().trim(),
                password = password.trim()
            )
        )
    }

    override suspend fun signUp(email: String, password: String): Result<AuthenticationEntity> {
        return authSource.signUp(
            AuthenticationEntity(
                email = email.lowercase().trim(),
                password = password.trim()
            )
        )
    }

    override suspend fun getCurrentAuthentication(): Result<AuthenticationEntity> {
        return authSource.getCurrentAuthentication()
    }
}
