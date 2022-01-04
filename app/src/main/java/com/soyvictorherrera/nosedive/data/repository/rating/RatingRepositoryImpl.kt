package com.soyvictorherrera.nosedive.data.repository.rating

import com.soyvictorherrera.nosedive.data.source.rating.RatingDataSource
import com.soyvictorherrera.nosedive.data.source.rating.RatingEntity
import com.soyvictorherrera.nosedive.util.Result

class RatingRepositoryImpl(
    private val ratingDataSource: RatingDataSource
) : RatingRepository {

    override suspend fun saveRating(ratedUserId: String, rating: RatingEntity): Result<Unit> {
        return ratingDataSource.saveRating(
            ratedUserId = ratedUserId,
            rating = rating
        )
    }

}
