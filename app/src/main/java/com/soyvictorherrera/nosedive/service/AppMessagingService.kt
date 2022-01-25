package com.soyvictorherrera.nosedive.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.NewFollowNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NewRatingNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.TokenModel
import com.soyvictorherrera.nosedive.domain.usecase.token.AddUserTokenUseCase
import com.soyvictorherrera.nosedive.util.NotificationUtil
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var addUserTokenUseCase: AddUserTokenUseCase

    @Inject
    lateinit var preferenceUtil: PreferenceUtil

    @Inject
    lateinit var notifications: NotificationUtil

    @Inject
    lateinit var notificationMapper: DomainMapper<JSONObject, NotificationModel>

    private val job: CompletableJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(newToken: String) {
        Timber.i("new token created: $newToken")
        cacheToken(newToken)
        addUserToken(newToken)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val payload: String = message.data["payload"] ?: return
        Timber.d("Message payload is: $payload")
        handleMessagePayload(payload)
    }

    private fun cacheToken(token: String) {
        preferenceUtil.setFcmToken(token)
    }

    private fun addUserToken(token: String) {
        preferenceUtil.getUserId()?.let { uid ->
            Timber.i("adding FCM token to user: $uid")
            scope.launch {
                val model = TokenModel(string = token)
                addUserTokenUseCase.apply {
                    this.userId = uid
                    this.token = model
                }.execute().let { result ->
                    if (result is Result.Error) Timber.e(result.exception)
                }
            }
        }
    }

    private fun handleMessagePayload(payload: String) {
        try {
            val jsonPayload = JSONObject(payload)
            val notification = notificationMapper.toDomainModel(jsonPayload)
            val notificationId = notification.id

            if (notification.seen != null) return

            when (notification) {
                is NewFollowNotificationModel -> notifications.displayNewFollowerNotification(
                    notificationId = notificationId
                )
                is NewRatingNotificationModel -> notifications.displayNewRatingNotification(
                    notificationId = notificationId,
                    rating = notification.ratingValue
                )
            }
        } catch (ex: Exception) {
            Timber.e("Unable to read notification payload. Expected a JSON, was: $payload")
        }
    }

    override fun onDestroy() {
        job.cancel("AppMessagingService destroyed")
        super.onDestroy()
    }

}
