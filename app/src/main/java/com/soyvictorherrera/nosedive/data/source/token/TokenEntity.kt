package com.soyvictorherrera.nosedive.data.source.token

import com.google.firebase.database.Exclude

data class TokenEntity(
    @get:Exclude var id: String ? = null,
    var string: String? = null,
    var registration: Long? = null
)
