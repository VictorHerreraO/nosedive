package com.soyvictorherrera.nosedive.data.source.friend

import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface FriendDataSource {

    fun observeFriendList(userId: String): Flow<Result<List<FriendEntity>>>

}
