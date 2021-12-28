package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.domain.model.UserScoreModel

class UserScoreEntityMapper : DomainMapper<UserScoreEntity, UserScoreModel>() {

    override fun toDomainModel(value: UserScoreEntity): UserScoreModel = with(value) {
        return UserScoreModel(
            sum = sum ?: 0,
            count = count ?: 0
        )
    }

    override fun fromDomainModel(model: UserScoreModel): UserScoreEntity = with(model) {
        return UserScoreEntity(
            sum = sum,
            count = count
        )
    }

}
