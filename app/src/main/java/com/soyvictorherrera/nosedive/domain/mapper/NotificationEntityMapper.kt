package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.notification.NotificationEntity
import com.soyvictorherrera.nosedive.domain.model.NewFollowNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NewRatingNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationType
import com.soyvictorherrera.nosedive.presentation.extensions.toEpochMilli
import com.soyvictorherrera.nosedive.presentation.extensions.toLocalDateTime

class NotificationEntityMapper : DomainMapper<NotificationEntity, NotificationModel>() {

    override fun toDomainModel(value: NotificationEntity): NotificationModel = with(value) {
        return when (type) {
            NotificationType.NEW_FOLLOW.toString() -> NewFollowNotificationModel(
                id = id!!,
                date = date!!.toLocalDateTime(),
                who = who ?: "",
                seen = seen?.toLocalDateTime(),
                followerName = data?.get("name")?.toString() ?: ""
            )
            NotificationType.NEW_RATING.toString() -> NewRatingNotificationModel(
                id = id!!,
                date = date!!.toLocalDateTime(),
                who = who ?: "",
                seen = seen?.toLocalDateTime(),
                raterName = data?.get("name")?.toString() ?: "",
                ratingValue = data?.get("rating")?.toString()?.toDouble() ?: 0.0
            )
            else -> throw UnsupportedOperationException("can't map from type $type")
        }
    }

    override fun fromDomainModel(model: NotificationModel): NotificationEntity = with(model) {
        return NotificationEntity(
            id = id,
            date = date.toEpochMilli(),
            type = type.toString(),
            who = who,
            seen = seen?.toEpochMilli(),
            data = mutableMapOf<String, Any>().apply {
                when (model) {
                    is NewFollowNotificationModel -> {
                        put("name", model.followerName)
                    }
                    is NewRatingNotificationModel -> {
                        put("name", model.raterName)
                        put("rating", model.ratingValue)
                    }
                }
            }
        )
    }

}
