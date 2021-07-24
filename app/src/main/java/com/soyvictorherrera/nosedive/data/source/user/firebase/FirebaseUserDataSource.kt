package com.soyvictorherrera.nosedive.data.source.user.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileInputStream
import java.net.URI


class FirebaseUserDataSource(
    private val users: DatabaseReference,
    private val userPhotos: StorageReference
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

    override suspend fun updateUserPhoto(userId: String, photo: File): Flow<Result<URI>> =
        callbackFlow {
            trySendBlocking(Result.Loading)

            val photoStream = FileInputStream(photo)
            val userRef = users.child(userId)
            val photoRef = userPhotos.child(userId).child("${photo.name}.png")
            val task = photoRef.putStream(photoStream)
            task.continueWithTask { uploadTask ->
                if (!uploadTask.isSuccessful) {
                    this@callbackFlow.trySendBlocking(
                        Result.Error(
                            uploadTask.exception ?: RuntimeException(
                                "unable to upload user photo"
                            )
                        )
                    )
                }
                photoRef.downloadUrl
            }.addOnCompleteListener { urlTask ->
                if (!urlTask.isSuccessful) {
                    this@callbackFlow.trySendBlocking(
                        Result.Error(
                            urlTask.exception ?: RuntimeException(
                                "unable to get download url"
                            )
                        )
                    )
                }
                val uriString = urlTask.result.toString()
                userRef.child("photoUrl").setValue(uriString)
                this@callbackFlow.trySendBlocking(
                    Result.Success(URI(uriString)).also {
                        Log.d(TAG, "trySendBlocking uri ${it.data}")
                    }
                )
            }

            awaitClose {
                // Cancel upload on flow dispose
                if (task.isInProgress) {
                    task.cancel()
                }
            }
        }

}
