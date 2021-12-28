package com.soyvictorherrera.nosedive.domain.model

import java.io.Serializable

class UserScoreModel(
    var sum: Int = 0,
    var count: Int = 0
) : Serializable {

    val average: Double
        get() = sum.toDouble() / count.toDouble()

}
