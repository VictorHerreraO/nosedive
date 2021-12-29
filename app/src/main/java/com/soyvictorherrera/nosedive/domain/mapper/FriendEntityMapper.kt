package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import java.net.URI

class FriendEntityMapper : DomainMapper<FriendEntity, FriendModel>() {

    override fun toDomainModel(value: FriendEntity): FriendModel = with(value) {
        return FriendModel(
            id = id!!,
            name = name!!,
            photoUrl = photoUrl?.let { URI(it) }
        )
    }

    override fun fromDomainModel(model: FriendModel): FriendEntity = with(model) {
        return FriendEntity(
            id = id,
            name = name,
            photoUrl = photoUrl?.toString()
        )
    }
}
