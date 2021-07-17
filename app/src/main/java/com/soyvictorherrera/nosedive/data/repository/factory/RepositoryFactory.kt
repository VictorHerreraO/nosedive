package com.soyvictorherrera.nosedive.data.repository.factory

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepositoryImpl
import com.soyvictorherrera.nosedive.data.source.factory.DataSourceFactory

@Deprecated("use Hilt injection to provide repositories")
object RepositoryFactory {

    fun getAuthenticationRepository(): AuthenticationRepository {
        return AuthenticationRepositoryImpl(DataSourceFactory.getAuthenticationDataSource())
    }

    fun getUserRepository(): UserRepository {
        return UserRepositoryImpl(DataSourceFactory.getUserDataSource())
    }

}
