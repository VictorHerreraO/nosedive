package com.soyvictorherrera.nosedive.data.source.factory

import com.google.firebase.auth.FirebaseAuth
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.firebase.FirebaseAuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.firebase.FirebaseUserDataSource

@Deprecated("use Hilt injection to provide data sources")
object DataSourceFactory {

    fun getAuthenticationDataSource(): AuthenticationDataSource {
        return FirebaseAuthenticationDataSource(FirebaseAuth.getInstance())
    }

    fun getUserDataSource(): UserDataSource {
        return FirebaseUserDataSource(FirebaseReferenceFactory.getUserReference())
    }

}
