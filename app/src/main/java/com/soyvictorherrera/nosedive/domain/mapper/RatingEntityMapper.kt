package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.rating.RatingEntity
import com.soyvictorherrera.nosedive.domain.model.RatingModel

class RatingEntityMapper : DomainMapper<RatingEntity, RatingModel>() {

    override fun toDomainModel(value: RatingEntity): RatingModel = with(value) {
        return RatingModel(
            id = id!!,
            value = this.value ?: 0,
            date = date ?: 0L,
            who = who ?: "",
            multiplier = multiplier ?: 1f
        )
    }

    override fun fromDomainModel(model: RatingModel): RatingEntity = with(model) {
        return RatingEntity(
            id = id,
            value = value,
            date = date,
            who = who,
            multiplier = multiplier
        )
    }
}
