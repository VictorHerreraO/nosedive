package com.soyvictorherrera.nosedive.data.source.notification.firebase

import com.google.firebase.database.*
import com.soyvictorherrera.nosedive.data.source.extensions.getValues
import com.soyvictorherrera.nosedive.data.source.notification.NotificationDataSource
import com.soyvictorherrera.nosedive.data.source.notification.NotificationEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class FirebaseNotificationDataSource(
    private val notifications: DatabaseReference
) : NotificationDataSource {
    private companion object {
        const val NOTIFICATION_COUNT_LIMIT = 20
    }

    override fun observeNotificationList(
        userId: String
    ): Flow<Result<List<NotificationEntity>>> = callbackFlow {
        trySendBlocking(Result.Loading)

        val notificationListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySendBlocking(Result.Success(data = snapshot.getValues()))
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(Result.Error(error.toException()))
            }
        }

        val notificationRef = notifications
            .child(userId)
            .limitToFirst(NOTIFICATION_COUNT_LIMIT)
        notificationRef.addValueEventListener(notificationListener)

        awaitClose {
            notificationRef.removeEventListener(notificationListener)
        }
    }

    override suspend fun dismissNotification(
        userId: String,
        notificationId: String
    ): Result<Unit> {
        return try {
            notifications.child(userId)
                .child(notificationId)
                .child(NotificationEntity::seen.name)
                .setValue(ServerValue.TIMESTAMP)
                .await()
            Result.Success(Unit)
        }catch (ex: Exception) {
            Result.Error(ex)
        }
    }

}
