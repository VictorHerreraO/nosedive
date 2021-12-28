package com.soyvictorherrera.nosedive.data.source.userStats.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsDataSource
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class FirebaseUserStatsDataSource(
    private val stats: DatabaseReference
) : UserStatsDataSource {

    override fun observeUserStats(userId: String): Flow<Result<UserStatsEntity>> = callbackFlow {
        val statsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stats = snapshot.getValue(UserStatsEntity::class.java)
                if (stats == null) {
                    this@callbackFlow.trySendBlocking(
                        Result.Error(RuntimeException("Unable to read user stats"))
                    )
                } else {
                    this@callbackFlow.trySendBlocking(
                        Result.Success(stats.copy(userId = userId))
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.Error(error.toException()))
            }
        }

        val statsRef = stats.child(userId)
        statsRef.addValueEventListener(statsListener)

        awaitClose {
            statsRef.removeEventListener(statsListener)
        }
    }

}
