package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel

class UserStatsEntityMapper : DomainMapper<UserStatsEntity, UserStatsModel>() {

    override fun toDomainModel(value: UserStatsEntity): UserStatsModel = with(value) {
        return UserStatsModel(
            followers = followers ?: 0,
            following = following ?: 0,
            ratings = ratings ?: 0,
            scoreSum = (scoreSum ?: 0f).toDouble()
        )
    }

    override fun fromDomainModel(model: UserStatsModel): UserStatsEntity = with(model) {
        return UserStatsEntity(
            following = following,
            followers = followers,
            ratings = ratings,
            scoreSum = scoreSum.toFloat()
        )
    }

}
