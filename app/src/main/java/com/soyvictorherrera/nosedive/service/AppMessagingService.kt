package com.soyvictorherrera.nosedive.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.soyvictorherrera.nosedive.domain.model.TokenModel
import com.soyvictorherrera.nosedive.domain.usecase.token.AddUserTokenUseCase
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var addUserTokenUseCase: AddUserTokenUseCase

    @Inject
    lateinit var preferenceUtil: PreferenceUtil

    private val job: CompletableJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(token: String) {
        Timber.i("new token created: $token")
        addUserToken(token)
    }

    private fun addUserToken(token: String) {
        preferenceUtil.getUserId(null)?.let { uid ->
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

    override fun onDestroy() {
        job.cancel("AppMessagingService destroyed")
        super.onDestroy()
    }

}
