package com.soyvictorherrera.nosedive.di

import com.google.firebase.FirebaseApp
import com.soyvictorherrera.nosedive.domain.resource.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.URI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): BaseUrl {
        return BaseUrl(
            value = URI("https://us-central1-${FirebaseApp.getInstance().options.projectId}.cloudfunctions.net")
        )
    }

}
