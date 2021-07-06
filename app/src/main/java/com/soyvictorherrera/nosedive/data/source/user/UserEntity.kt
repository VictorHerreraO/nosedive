package com.soyvictorherrera.nosedive.data.source.user

import com.google.firebase.database.Exclude

data class UserEntity(
    @get:Exclude val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val photoUrl: String? = null
)
