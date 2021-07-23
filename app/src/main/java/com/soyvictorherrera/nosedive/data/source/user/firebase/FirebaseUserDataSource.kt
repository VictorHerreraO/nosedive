package com.soyvictorherrera.nosedive.data.source.user.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import java.io.File
import java.net.URI


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

    override suspend fun observeUser(userId: String): Flow<Result<UserEntity>> = callbackFlow {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserEntity::class.java)
                if (user == null) {
                    this@callbackFlow.trySendBlocking(
                        Result.Error(RuntimeException("unable to read user {$userId}"))
                    )
                } else {
                    this@callbackFlow.trySendBlocking(
                        Result.Success(user)
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.Error(error.toException()))
            }
        }

        val userRef = users.child(userId)
        userRef.addValueEventListener(userListener)

        awaitClose {
            userRef.removeEventListener(userListener)
        }
    }

    override suspend fun updateUserPhoto(userId: String, photo: File): Flow<Result<URI>> {
        Log.w(TAG, "update photo here!")
        return flowOf(Result.Success(data = URI("https://thiscatdoesnotexist.com/")))
    }
}
