package com.soyvictorherrera.nosedive.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
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
        // TODO: 21/01/2022 leer el tipo de notificación y mostrar el mensaje correspondiente
        notifications.displayNewRatingNotification()
    }

    override fun onDestroy() {
        job.cancel("AppMessagingService destroyed")
        super.onDestroy()
    }

}
