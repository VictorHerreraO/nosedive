package com.soyvictorherrera.nosedive.domain.model

import java.io.Serializable

class RatingModel(
    val id: String,
    var value: Int,
    var date: Long,
    var who: String,
    var multiplier: Float = 1f
) : Serializable
