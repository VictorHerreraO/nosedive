package com.soyvictorherrera.nosedive.domain.usecase.authentication

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationEntity
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.model.TokenModel
import com.soyvictorherrera.nosedive.domain.usecase.token.AddUserTokenUseCase
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result
import timber.log.Timber

class SignInUseCase(
    private val authRepository: AuthenticationRepository,
    private val preferences: PreferenceUtil,
    private val addUserTokenUseCase: AddUserTokenUseCase
) {

    suspend operator fun invoke(user: UserEntity): Result<AuthenticationEntity> {
        val email = user.email ?: return Result.Error(
            RuntimeException("{email} must not be null")
        )
        val pwd = user.password ?: return Result.Error(
            RuntimeException("{password} must not be null")
        )
        return authRepository.signIn(email = email, password = pwd)
            .also {
                if (it is Result.Success) preferences.getFcmToken()?.let { token ->
                    val uid = it.data.userId
                    Timber.i("adding FCM token to user: $uid")
                    addUserTokenUseCase.apply {
                        this.userId = uid
                        this.token = TokenModel(string = token)
                    }.execute().let { result ->
                        if (result is Result.Error) Timber.e(result.exception)
                    }
                }
            }
            .also {
                preferences.setSessionOpen(it is Result.Success)
            }
    }

}
