package com.soyvictorherrera.nosedive.data.source.rating

import com.google.firebase.database.Exclude

data class RatingEntity(
    @get:Exclude var id: String? = null,
    var value: Int? = null,
    var date: Long? = null,
    var who: String? = null,
    var multiplier: Float? = null
)
