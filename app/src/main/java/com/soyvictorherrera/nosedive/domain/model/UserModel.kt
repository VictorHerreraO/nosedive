package com.soyvictorherrera.nosedive.domain.model

import android.net.Uri

enum class UserStatus { ACTIVE, PENALIZED, BLOCKED }

class UserModel(
    var id: String? = null,
    var name: String,
    var email: String,
    var password: String? = null,
    var photoUrl: Uri? = null,
    var status: UserStatus = UserStatus.ACTIVE,
    var score: Double = 0.0
)
