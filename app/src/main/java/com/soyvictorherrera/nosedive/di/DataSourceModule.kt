package com.soyvictorherrera.nosedive.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.firebase.FirebaseAuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.firebase.FirebaseUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    //region Firebase instances
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }
    //endregion

    //region Firebase database references
    @Provides
    @Singleton
    @Named("userRef")
    fun provideUserDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference("user")
    }
    //endregion

    //region Data sources
    @Provides
    @Singleton
    fun provideAuthenticationDataSource(auth: FirebaseAuth): AuthenticationDataSource {
        return FirebaseAuthenticationDataSource(auth = auth)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(@Named("userRef") users: DatabaseReference): UserDataSource {
        return FirebaseUserDataSource(users = users)
    }
    //endregion

}
