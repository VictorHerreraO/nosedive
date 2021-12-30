package com.soyvictorherrera.nosedive.domain.model

import java.net.URI

enum class UserStatus { ACTIVE, PENALIZED, BLOCKED }

class UserModel(
    var id: String? = null,
    var name: String,
    var email: String,
    var password: String? = null,
    var photoUrl: URI? = null,
    var status: UserStatus = UserStatus.ACTIVE,
    var score: Double? = null
)
