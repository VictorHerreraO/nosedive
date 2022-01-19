package com.soyvictorherrera.nosedive.domain.model

import java.io.Serializable
import java.net.URI
import java.time.LocalDateTime

enum class NotificationType {
    NEW_FOLLOW,
    NEW_RATING,
    OTHER
}

sealed class NotificationModel(
    val id: String,
    val date: LocalDateTime,
    val type: NotificationType,
    val who: String,
    val seen: LocalDateTime? = null
) : Serializable {
    var user: UserModel? = null
}

class NewFollowNotificationModel(
    id: String,
    date: LocalDateTime,
    who: String,
    seen: LocalDateTime? = null
) : NotificationModel(
    id = id,
    date = date,
    who = who,
    seen = seen,
    type = NotificationType.NEW_FOLLOW
) {
    var canFollowBack: Boolean = false
}

class NewRatingNotificationModel(
    id: String,
    date: LocalDateTime,
    who: String,
    seen: LocalDateTime? = null,
    val ratingValue: Int
) : NotificationModel(
    id = id,
    date = date,
    who = who,
    seen = seen,
    type = NotificationType.NEW_RATING
)
