package com.soyvictorherrera.nosedive.data.repository.friend

import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    fun observeFriendList(userId: String): Flow<Result<List<FriendEntity>>>

    suspend fun addFriend(userId: String, friendEntity: FriendEntity): Result<Unit>

}
