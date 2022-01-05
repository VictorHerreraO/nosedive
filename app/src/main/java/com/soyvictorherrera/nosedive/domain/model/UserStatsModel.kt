package com.soyvictorherrera.nosedive.domain.model

import java.io.Serializable

class UserStatsModel(
    var followers: Int = 0,
    var ratings: Int = 0,
    var following: Int = 0,
    var scoreSum: Double = 0.0
) : Serializable {

    companion object {
        const val REQUIRED_RATINGS_COUNT = 10
    }

    val hasRequiredRatingsCount: Boolean
        get() = ratings >= REQUIRED_RATINGS_COUNT

    val averageScore: Double
        get() = scoreSum / ratings

}
