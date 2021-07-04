package com.soyvictorherrera.nosedive.data.source.user

import com.soyvictorherrera.nosedive.data.Result

interface UserDataSource {

    fun signInUser(request: UserEntity): Result<UserEntity>

    fun signUpUser(request: UserEntity): Result<UserEntity>

}
