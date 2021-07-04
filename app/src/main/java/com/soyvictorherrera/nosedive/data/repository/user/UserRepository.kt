package com.soyvictorherrera.nosedive.data.repository.user

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

interface UserRepository {

    fun signInUser(user: UserEntity): Result<UserEntity>

    fun signUpUser(user: UserEntity): Result<UserEntity>

}
