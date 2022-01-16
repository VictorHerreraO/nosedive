package com.soyvictorherrera.nosedive.data.source.notification

import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {

    fun observeNotificationList(userId: String): Flow<Result<List<NotificationEntity>>>

    suspend fun dismissNotification(userId: String, notificationId: String): Result<Unit>

}
