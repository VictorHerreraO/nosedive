package com.soyvictorherrera.nosedive.domain.model

import com.soyvictorherrera.nosedive.presentation.extensions.toLocalDateTime
import java.io.Serializable
import java.net.URI
import java.time.LocalDateTime

class FriendModel(
    val id: String,
    var name: String,
    var photoUrl: URI? = null,
    var score: Double? = null,
    val lastRated: Long? = null
) : Serializable {

    val lastRatedParsed: LocalDateTime? by lazy {
        lastRated?.toLocalDateTime()
    }

    val anonymous: Boolean get() = name.isEmpty()

}
