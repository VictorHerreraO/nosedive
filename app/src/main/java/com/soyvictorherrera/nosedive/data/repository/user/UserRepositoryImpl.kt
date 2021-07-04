package com.soyvictorherrera.nosedive.data.repository.user

import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserEntity

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {


    override fun signInUser(user: UserEntity): Result<UserEntity> {
        return userDataSource.signInUser(user)
    }

    override fun signUpUser(user: UserEntity): Result<UserEntity> {
        return userDataSource.signUpUser(user)
    }
}