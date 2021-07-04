package com.soyvictorherrera.nosedive.data.source.factory

import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.firebase.FirebaseUserDataSource

object DataSourceFactory {

    fun getUserDataSource(): UserDataSource {
        return FirebaseUserDataSource()
    }


}