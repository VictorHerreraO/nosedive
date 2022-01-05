package com.soyvictorherrera.nosedive.domain.model

import java.io.Serializable

@Deprecated("Use UseStatsModel instead")
class UserScoreModel(
    var sum: Int = 0,
    var count: Int = 0
) : Serializable {

    companion object {
        const val REQUIRED_COUNT = 10
    }

    val average: Double
        get() = sum.toDouble() / count.toDouble()

}
