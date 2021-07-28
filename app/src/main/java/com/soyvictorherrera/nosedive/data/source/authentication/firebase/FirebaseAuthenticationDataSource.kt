package com.soyvictorherrera.nosedive.data.source.authentication.firebase

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
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

    override suspend fun updateUserPassword(
        password: String,
        newPassword: String
    ): Flow<Result<Boolean>> = callbackFlow {
        send(Result.Loading)

        auth.currentUser?.let { currentUser ->
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, password)
            // Reauthenticate user
            currentUser.reauthenticate(credential)
                .continueWithTask { authTask ->
                    if (!authTask.isSuccessful) {
                        cancel(
                            message = "unable to re-authenticate user",
                            cause = authTask.exception
                        )
                    }
                    // Update user password
                    currentUser.updatePassword(newPassword)
                }
                .addOnCompleteListener { pwdTask ->
                    if (!pwdTask.isSuccessful) {
                        cancel(
                            message = "unable to change user password",
                            cause = pwdTask.exception
                        )
                    }
                    trySendBlocking(Result.Success(true))
                }
        } ?: cancel(message = "no signed user", cause = IllegalStateException())

        awaitClose {
            // Do nothing
        }
    }

}

private fun FirebaseUser.toAuthenticationEntity(): AuthenticationEntity {
    return AuthenticationEntity(
        userId = uid,
        email = email,
        password = null
    )
}
