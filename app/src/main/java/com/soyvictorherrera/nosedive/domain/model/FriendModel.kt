package com.soyvictorherrera.nosedive.domain.model

import java.net.URI

class FriendModel(
    val id: String,
    var name: String,
    var photoUrl: URI? = null,
    var score: Double? = null
)
