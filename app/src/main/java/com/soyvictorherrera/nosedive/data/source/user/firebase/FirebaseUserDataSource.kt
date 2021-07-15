package com.soyvictorherrera.nosedive.data.source.user.firebase

import com.google.firebase.database.DatabaseReference
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class FirebaseUserDataSource(
    private val users: DatabaseReference
) : UserDataSource {

    override suspend fun saveUser(user: UserEntity): Result<UserEntity> {
        val safeUser = user.copy()
        val uid = safeUser.id ?: return Result.Error(
            RuntimeException("{user.id} must not be null or empty")
        )

        return try {
            users.child(uid).setValue(safeUser).await()

            Result.Success(safeUser)
        } catch (ex: CancellationException) {
            Result.Error(ex)
        }
    }

    override suspend fun getUser(userId: String): Flow<Result<UserEntity>> = flow {
        val user = users.child(userId)
            .get()
            .await()
            ?.getValue(UserEntity::class.java)
            ?: throw RuntimeException("unable to read user {$userId}")

        emit(Result.Success(user.copy(id = userId)))
    }
}
