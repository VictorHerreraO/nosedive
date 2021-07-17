package com.soyvictorherrera.nosedive.data.source.authentication.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticationDataSource(
    private val auth: FirebaseAuth
) : AuthenticationDataSource {

    override suspend fun signIn(request: AuthenticationEntity): Result<AuthenticationEntity> {
        return try {
            val email = request.email ?: throw IllegalArgumentException(
                "{email} must not be null or empty"
            )
            val pwd = request.password ?: throw IllegalArgumentException(
                "{password} must not be null or empty"
            )
            val user = auth.signInWithEmailAndPassword(email, pwd)
                .await()?.user ?: throw (RuntimeException("unable to sign in"))

            Result.Success(user.toAuthenticationEntity())
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

    override suspend fun signUp(request: AuthenticationEntity): Result<AuthenticationEntity> {
        return try {
            val email = request.email ?: throw IllegalArgumentException(
                "{email} must not be null or empty"
            )
            val pwd = request.password ?: throw IllegalArgumentException(
                "{password} must not be null or empty"
            )
            val user = auth.createUserWithEmailAndPassword(email, pwd)
                .await()?.user ?: throw RuntimeException("unable to sign up")

            return Result.Success(user.toAuthenticationEntity())
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

    override suspend fun getCurrentAuthentication(): Flow<Result<AuthenticationEntity>> = flow {
        auth.currentUser?.let { currentUser ->
            emit(Result.Success(currentUser.toAuthenticationEntity()))
        } ?: throw RuntimeException("no signed user")
    }
}

private fun FirebaseUser.toAuthenticationEntity(): AuthenticationEntity {
    return AuthenticationEntity(
        userId = uid,
        email = email,
        password = null
    )
}
