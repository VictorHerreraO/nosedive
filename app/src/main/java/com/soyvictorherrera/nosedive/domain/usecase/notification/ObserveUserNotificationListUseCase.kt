package com.soyvictorherrera.nosedive.domain.usecase.notification

import com.soyvictorherrera.nosedive.data.repository.notification.NotificationRepository
import com.soyvictorherrera.nosedive.data.source.notification.NotificationEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObserveUserNotificationListUseCase(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: DomainMapper<NotificationEntity, NotificationModel>
) : BaseUseCase<Result<List<NotificationModel>>>() {

    var userId: String? = null

    override suspend fun buildFlow(): Flow<Result<List<NotificationModel>>> {
        return userId.let { id ->
            if (id.isNullOrEmpty()) flowOf(
                Result.Error(
                    IllegalArgumentException("[userId] must not be null or empty")
                )
            ) else notificationRepository
                .observeNotificationList(userId = id)
                .map { result ->
                    result.map(notificationMapper::toDomainModelList)
                }
        }
    }
}
