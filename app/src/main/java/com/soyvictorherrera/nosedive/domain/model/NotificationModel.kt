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
) : Serializable

class NewFollowNotificationModel(
    id: String,
    date: LocalDateTime,
    who: String,
    seen: LocalDateTime? = null,
    val followerName: String,
    val photoUrl: URI?
) : NotificationModel(
    id = id,
    date = date,
    who = who,
    seen = seen,
    type = NotificationType.NEW_FOLLOW
)

class NewRatingNotificationModel(
    id: String,
    date: LocalDateTime,
    who: String,
    seen: LocalDateTime? = null,
    val raterName: String,
    val ratingValue: Int,
    val photoUrl: URI?
) : NotificationModel(
    id = id,
    date = date,
    who = who,
    seen = seen,
    type = NotificationType.NEW_RATING
)
