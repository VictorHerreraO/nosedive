package com.soyvictorherrera.nosedive.data.source.user.firebase

import com.google.firebase.database.DatabaseReference
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity


class FirebaseUserDataSource(
    private val users: DatabaseReference
) : UserDataSource {
    private companion object {
        private var user: UserEntity? = null
    }

    override fun signInUser(request: UserEntity): Result<UserEntity> {
        // TODO: 04/07/2021 leer desde firebase
        return user?.let { u ->
            if (u.email != request.email || u.password != request.password)
                Result.Error(RuntimeException("credentials don't match"))
            else
                Result.Success(u)
        } ?: Result.Error(IllegalStateException("no user registered"))
    }

    override fun signUpUser(request: UserEntity): Result<UserEntity> {
        // TODO: 04/07/2021 encriptar la contraseña - usar bcrypt o algo
        val ref = users.push()
        val safe = UserEntity(
            id = ref.key,
            name = request.name?.trim(),
            email = request.email?.lowercase()?.trim(),
            password = request.password?.trim(),
            photoUrl = request.photoUrl?.trim()
        )
        ref.setValue(safe)
        user = safe

        return Result.Success(safe)
    }
}
