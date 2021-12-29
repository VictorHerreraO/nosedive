package com.soyvictorherrera.nosedive.data.source.friend.firebase

import com.google.firebase.database.*
import com.soyvictorherrera.nosedive.data.source.extensions.forEach
import com.soyvictorherrera.nosedive.data.source.friend.FriendDataSource
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class FirebaseFriendDataSource(
    private val friends: DatabaseReference
) : FriendDataSource {

    override fun observeFriendList(userId: String): Flow<Result<List<FriendEntity>>> =
        callbackFlow {
            this.trySendBlocking(Result.Loading)

            val friendListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val friendList = mutableListOf<FriendEntity>().apply {
                        snapshot.forEach { child ->
                            child.getValue(FriendEntity::class.java)
                                ?.apply { id = child.key!! }
                                ?.also { add(it) }
                        }
                    }
                    this@callbackFlow.trySendBlocking(Result.Success(friendList))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(
                        Result.Error(error.toException())
                    )
                }
            }

            val friendRef = friends.child(userId)
            friendRef.addValueEventListener(friendListener)

            awaitClose {
                friendRef.removeEventListener(friendListener)
            }
        }

}
