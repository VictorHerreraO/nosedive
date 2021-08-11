package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatus
import java.net.URI

class UserEntityMapper : DomainMapper<UserEntity, UserModel>() {

    override fun toDomainModel(value: UserEntity): UserModel = with(value) {
        return UserModel(
            id = id,
            name = name ?: "",
            email = email ?: "",
            password = password,
            photoUrl = photoUrl?.let { url -> URI(url) },
            status = status?.let { UserStatus.valueOf(it) } ?: UserStatus.BLOCKED,
            score = score ?: 0.0
        )
    }

    override fun fromDomainModel(model: UserModel): UserEntity = with(model) {
        return UserEntity(
            id = id,
            name = name,
            email = email,
            password = password,
            photoUrl = photoUrl?.toString(),
            status = status.toString(),
            score = score,
        )
    }

}
