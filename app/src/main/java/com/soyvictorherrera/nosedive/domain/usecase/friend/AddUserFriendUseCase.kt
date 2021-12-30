package com.soyvictorherrera.nosedive.domain.usecase.friend

import com.soyvictorherrera.nosedive.data.repository.friend.FriendRepository
import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.util.Result

class AddUserFriendUseCase(
    private val friendRepository: FriendRepository,
    private val friendEntityMapper: DomainMapper<FriendEntity, FriendModel>
) {

    var userId: String? = null
    var friend: FriendModel? = null

    suspend fun execute(): Result<Unit> {
        val safeId = userId
        if (safeId.isNullOrEmpty()) return Result.Error(
            IllegalArgumentException("[userId] must not be null or empty")
        )
        val safeFriend = friend ?: return Result.Error(
            IllegalArgumentException("[friend] must not be null")
        )

        return friendRepository.addFriend(
            userId = safeId,
            friendEntity = friendEntityMapper.fromDomainModel(safeFriend)
        )
    }

}
