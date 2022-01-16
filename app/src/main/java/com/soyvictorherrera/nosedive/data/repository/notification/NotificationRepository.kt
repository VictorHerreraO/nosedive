package com.soyvictorherrera.nosedive.data.repository.notification

import com.soyvictorherrera.nosedive.data.source.notification.NotificationEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun observeNotificationList(userId: String): Flow<Result<List<NotificationEntity>>>

    suspend fun dismissNotification(userId: String, notificationId: String): Result<Unit>

}
