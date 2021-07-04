package com.soyvictorherrera.nosedive.data.repository.factory

import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepositoryImpl
import com.soyvictorherrera.nosedive.data.source.factory.DataSourceFactory

object RepositoryFactory {

    fun getUserRepository(): UserRepository {
        return UserRepositoryImpl(DataSourceFactory.getUserDataSource())
    }

}
