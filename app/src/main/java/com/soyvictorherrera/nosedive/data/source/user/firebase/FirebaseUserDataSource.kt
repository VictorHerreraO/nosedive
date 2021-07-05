package com.soyvictorherrera.nosedive.data.source.user.firebase

import com.google.firebase.database.DatabaseReference
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import kotlinx.coroutines.tasks.await


class FirebaseUserDataSource(
    private val users: DatabaseReference
) : UserDataSource {

    override suspend fun saveUser(user: UserEntity): Result<UserEntity> {
        val uid = user.id ?: return Result.Error(
            RuntimeException("{user.id} must not be null or empty")
        )
        val value = user.copy(id = uid)

        users.child(uid).setValue(user.copy(id = null)).await()

        return Result.Success(value)
    }

    override suspend fun getUser(userId: String): Result<UserEntity> {
        val user = users.child(userId)
            .get()
            .await()
            ?.getValue(UserEntity::class.java)
            ?: return Result.Error(
                RuntimeException("unable to read user {$userId}")
            )

        return Result.Success(user.copy(id = userId))
    }

}
