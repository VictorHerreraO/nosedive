package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatus

class UserEntityMapper : DomainMapper<UserEntity, UserModel>() {

    override fun toDomainModel(value: UserEntity): UserModel = with(value) {
        return UserModel(
            id = id,
            name = name ?: "",
            email = email ?: "",
            password = password,
            photoUrl = photoUrl,
            status = if (status.isNullOrEmpty()) UserStatus.BLOCKED else UserStatus.valueOf(status),
            score = score ?: 0.0
        )
    }

    override fun fromDomainModel(model: UserModel): UserEntity = with(model) {
        return UserEntity(
            id = id,
            name = name,
            email = email,
            password = password,
            photoUrl = photoUrl,
            status = status.toString(),
            score = score,
        )
    }

}
