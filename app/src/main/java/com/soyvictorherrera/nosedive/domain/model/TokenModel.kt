package com.soyvictorherrera.nosedive.domain.model

import com.soyvictorherrera.nosedive.presentation.extensions.toLocalDateTime
import java.io.Serializable
import java.time.LocalDateTime

class TokenModel(
    var id: String? = null,
    var string: String,
    var registration: Long? = null
) : Serializable {

    val registrationDate: LocalDateTime?
        get() = registration?.toLocalDateTime()

}
