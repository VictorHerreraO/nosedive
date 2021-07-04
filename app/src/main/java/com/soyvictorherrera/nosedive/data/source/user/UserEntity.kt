package com.soyvictorherrera.nosedive.data.source.user

data class UserEntity(
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String? = null,
    val photoUrl: String? = null
)
