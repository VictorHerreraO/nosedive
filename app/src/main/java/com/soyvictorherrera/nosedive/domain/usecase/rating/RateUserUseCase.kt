package com.soyvictorherrera.nosedive.domain.usecase.rating

import com.soyvictorherrera.nosedive.data.repository.rating.RatingRepository
import com.soyvictorherrera.nosedive.data.source.rating.RatingEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.RatingModel
import com.soyvictorherrera.nosedive.util.Result

class RateUserUseCase(
    private val ratingRepository: RatingRepository,
    private val ratingEntityMapper: DomainMapper<RatingEntity, RatingModel>
) {
    var ratedUserId: String? = null
    var rating: RatingModel? = null

    suspend fun execute(): Result<Unit> {
        val safeId = ratedUserId
        if (safeId.isNullOrEmpty()) return Result.Error(
            IllegalArgumentException("[ratedUserId] must not be null or empty")
        )
        val safeRating = rating ?: return Result.Error(
            IllegalArgumentException("[rating] must not be null")
        )
        return ratingRepository.saveRating(
            ratedUserId = safeId,
            rating = ratingEntityMapper.fromDomainModel(safeRating)
        )
    }

}
