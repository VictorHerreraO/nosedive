package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.domain.mapper.*
import com.soyvictorherrera.nosedive.domain.model.SharingCodeModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserScoreModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    @Singleton
    fun provideUserEntityMapper(): DomainMapper<UserEntity, UserModel> {
        return UserEntityMapper()
    }

    @Provides
    @Singleton
    fun provideSharingCodeEntityMapper(): DomainMapper<SharingCodeEntity, SharingCodeModel> {
        return SharingCodeEntityMapper()
    }

    @Provides
    @Singleton
    fun provideUserStatsEntityMapper(): DomainMapper<UserStatsEntity, UserStatsModel> {
        return UserStatsEntityMapper()
    }

    @Provides
    @Singleton
    fun provideUserScoreEntityMapper(): DomainMapper<UserScoreEntity, UserScoreModel> {
        return UserScoreEntityMapper()
    }

}
