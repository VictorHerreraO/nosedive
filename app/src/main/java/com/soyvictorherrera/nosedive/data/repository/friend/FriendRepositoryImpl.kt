package com.soyvictorherrera.nosedive.data.repository.friend

import com.soyvictorherrera.nosedive.data.source.friend.FriendDataSource
import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

class FriendRepositoryImpl(
    private val friendDataSource: FriendDataSource
) : FriendRepository {

    override fun observeFriendList(userId: String): Flow<Result<List<FriendEntity>>> {
        return friendDataSource.observeFriendList(userId)
    }

}
