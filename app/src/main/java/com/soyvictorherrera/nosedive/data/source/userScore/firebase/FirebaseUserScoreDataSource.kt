package com.soyvictorherrera.nosedive.data.source.userScore.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreDataSource
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class FirebaseUserScoreDataSource(
    private val scores: DatabaseReference
) : UserScoreDataSource {

    override fun observeUserScore(userId: String): Flow<Result<UserScoreEntity>> = callbackFlow {
        val scoreListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val score = snapshot.getValue(UserScoreEntity::class.java)
                if (score == null) this@callbackFlow.trySendBlocking(
                    Result.Error(RuntimeException("unable to read user score, userId was [$userId]"))
                ) else this@callbackFlow.trySendBlocking(
                    Result.Success(score)
                )
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(
                    Result.Error(error.toException())
                )
            }
        }

        val scoreRef = scores.child(userId)
        scoreRef.addValueEventListener(scoreListener)

        awaitClose {
            scoreRef.removeEventListener(scoreListener)
        }
    }
}
