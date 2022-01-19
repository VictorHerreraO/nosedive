package com.soyvictorherrera.nosedive.domain.usecase.notification

import com.soyvictorherrera.nosedive.data.repository.notification.NotificationRepository
import com.soyvictorherrera.nosedive.util.Result

class DismissNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {

    var userId: String? = null
    var notificationId: String? = null

    suspend fun execute(): Result<Unit> {
        val safeId = userId
        if (safeId.isNullOrEmpty()) return Result.Error(
            IllegalArgumentException("[userId] must not be null or empty")
        )
        val safeNotificationId = notificationId
        if (safeNotificationId.isNullOrEmpty()) return Result.Error(
            IllegalArgumentException("[notificationId] must not be null or empty")
        )

        return notificationRepository.dismissNotification(
            userId = safeId,
            notificationId = safeNotificationId
        )
    }
}