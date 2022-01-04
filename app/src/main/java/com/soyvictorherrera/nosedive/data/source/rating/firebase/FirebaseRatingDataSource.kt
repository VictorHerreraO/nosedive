package com.soyvictorherrera.nosedive.data.source.rating.firebase

import com.google.firebase.database.DatabaseReference
import com.soyvictorherrera.nosedive.data.source.rating.RatingDataSource
import com.soyvictorherrera.nosedive.data.source.rating.RatingEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.tasks.await

class FirebaseRatingDataSource(
    private val ratings: DatabaseReference
) : RatingDataSource {

    override suspend fun saveRating(ratedUserId: String, rating: RatingEntity): Result<Unit> {
        return try {
            ratings.child(ratedUserId)
                .push()
                .setValue(rating)
                .await()
            Result.Success(Unit)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

}
