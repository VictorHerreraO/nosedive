package com.soyvictorherrera.nosedive.data.repository.rating

import com.soyvictorherrera.nosedive.data.source.rating.RatingEntity
import com.soyvictorherrera.nosedive.util.Result

interface RatingRepository {

    suspend fun saveRating(ratedUserId: String, rating: RatingEntity): Result<Unit>

}
