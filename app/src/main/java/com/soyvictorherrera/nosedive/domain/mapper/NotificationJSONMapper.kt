package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.domain.model.NewFollowNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NewRatingNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationType
import com.soyvictorherrera.nosedive.presentation.extensions.toEpochMilli
import com.soyvictorherrera.nosedive.presentation.extensions.toLocalDateTime
import org.json.JSONObject

class NotificationJSONMapper : DomainMapper<JSONObject, NotificationModel>() {
    private object Key {
        const val ID = "id"
        const val WHO = "who"
        const val TYPE = "type"
        const val DATE = "date"
        const val DATA = "data"
        const val RATING = "rating"
        const val SEEN = "seen"
    }

    override fun toDomainModel(value: JSONObject): NotificationModel = with(value) {
        return when (getString(Key.TYPE)) {
            NotificationType.NEW_RATING.toString() -> NewRatingNotificationModel(
                id = getString(Key.ID),
                date = getLong(Key.DATE).toLocalDateTime(),
                who = getString(Key.WHO),
                seen = optLong(Key.SEEN, -1L).let { long ->
                    if (long != -1L) long.toLocalDateTime() else null
                },
                ratingValue = getJSONObject(Key.DATA).getInt(Key.RATING)
            )
            NotificationType.NEW_FOLLOW.toString() -> NewFollowNotificationModel(
                id = getString(Key.ID),
                date = getLong(Key.DATE).toLocalDateTime(),
                who = getString(Key.WHO),
                seen = optLong(Key.SEEN, -1L).let { long ->
                    if (long != -1L) long.toLocalDateTime() else null
                }
            )
            else -> throw IllegalArgumentException("unknown notification type")
        }
    }

    override fun fromDomainModel(model: NotificationModel): JSONObject {
        return JSONObject().apply {
            put(Key.ID, model.id)
            put(Key.WHO, model.who)
            put(Key.TYPE, model.type)
            put(Key.DATE, model.date.toEpochMilli())
            put(Key.SEEN, model.seen?.toEpochMilli())

            when (model) {
                is NewRatingNotificationModel -> put(
                    Key.DATA,
                    JSONObject().put(Key.RATING, model.ratingValue)
                )
                else -> Unit
            }
        }
    }
}
