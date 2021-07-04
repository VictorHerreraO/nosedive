package com.soyvictorherrera.nosedive.data.source.user.firebase

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import java.util.*


class FirebaseUserDataSource : UserDataSource {
    private companion object {
        private var user: UserEntity? = null
    }

    override fun signInUser(request: UserEntity): Result<UserEntity> {
        return user?.let { u ->
            if (u.email != request.email || u.password != request.password)
                Result.Error(RuntimeException("credentials don't match"))
            else
                Result.Success(u)
        }
            ?: Result.Error(IllegalStateException("no user registered"))
    }

    override fun signUpUser(request: UserEntity): Result<UserEntity> {
        return Result.Success(UserEntity(
            id = UUID.randomUUID().toString(),
            name = request.name,
            email = request.email,
            password = request.password,
            photoUrl = request.photoUrl
        ).also { user = it })
    }
}