package com.soyvictorherrera.nosedive.data.source.rating

import com.soyvictorherrera.nosedive.util.Result

interface RatingDataSource {

    suspend fun saveRating(ratedUserId: String, rating: RatingEntity): Result<Unit>

}
