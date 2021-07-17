package com.soyvictorherrera.nosedive.domain.model

enum class UserStatus { ACTIVE, PENALIZED, BLOCKED }

class UserModel(
    var id: String? = null,
    var name: String,
    var email: String,
    var password: String? = null,
    var photoUrl: String? = null,
    var status: UserStatus = UserStatus.ACTIVE,
    var score: Double = 0.0
)
