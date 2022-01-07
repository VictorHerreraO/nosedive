package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.resource.BaseUrl

class FriendEntityMapper(
    private val baseUrl: BaseUrl
) : DomainMapper<FriendEntity, FriendModel>() {

    override fun toDomainModel(value: FriendEntity): FriendModel = with(value) {
        return FriendModel(
            id = id!!,
            name = name ?: "",
            photoUrl = baseUrl.append("/serveUserPhoto?uid=$id"),
            lastRated = lastRated
        )
    }

    override fun fromDomainModel(model: FriendModel): FriendEntity = with(model) {
        return FriendEntity(
            id = id,
            name = name,
            photoUrl = null,
            lastRated = lastRated
        )
    }
}
