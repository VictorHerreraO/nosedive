package com.soyvictorherrera.nosedive.data.repository.notification

import com.soyvictorherrera.nosedive.data.source.notification.NotificationDataSource
import com.soyvictorherrera.nosedive.data.source.notification.NotificationEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {

    override fun observeNotificationList(userId: String): Flow<Result<List<NotificationEntity>>> {
        return notificationDataSource.observeNotificationList(userId = userId)
    }

    override suspend fun dismissNotification(userId: String, notificationId: String): Result<Unit> {
        return notificationDataSource.dismissNotification(
            userId = userId,
            notificationId = notificationId
        )
    }

}
