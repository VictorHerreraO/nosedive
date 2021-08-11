package com.soyvictorherrera.nosedive.data.source.user

import com.google.firebase.database.Exclude

data class UserEntity(
    @get:Exclude var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var photoUrl: String? = null,
    var status: String? = null,
    var score: Double? = null
)
