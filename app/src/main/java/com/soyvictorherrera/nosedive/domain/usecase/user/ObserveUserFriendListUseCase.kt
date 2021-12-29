package com.soyvictorherrera.nosedive.domain.usecase.user

import com.soyvictorherrera.nosedive.data.repository.friend.FriendRepository
import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObserveUserFriendListUseCase(
    private val friendRepository: FriendRepository,
    private val friendEntityMapper: DomainMapper<FriendEntity, FriendModel>
) : BaseUseCase<Result<List<FriendModel>>>() {

    var userId: String? = null

    override suspend fun buildFlow(): Flow<Result<List<FriendModel>>> {
        return userId.let { id ->
            if (id.isNullOrEmpty()) flowOf(
                Result.Error(IllegalArgumentException("[userId] must not be null or empty"))
            ) else friendRepository.observeFriendList(userId = id)
                .map {
                    it.map(friendEntityMapper::toDomainModelList)
                }
        }
    }
}
