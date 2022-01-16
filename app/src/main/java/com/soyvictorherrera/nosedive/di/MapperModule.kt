package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.source.friend.firebase.FriendEntity
import com.soyvictorherrera.nosedive.data.source.notification.NotificationEntity
import com.soyvictorherrera.nosedive.data.source.rating.RatingEntity
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.domain.mapper.*
import com.soyvictorherrera.nosedive.domain.model.*
import com.soyvictorherrera.nosedive.domain.resource.BaseUrl
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
    fun provideFriendEntityMapper(baseUrl: BaseUrl): DomainMapper<FriendEntity, FriendModel> {
        return FriendEntityMapper(baseUrl = baseUrl)
    }

    @Provides
    @Singleton
    fun provideRatingEntityMapper(): DomainMapper<RatingEntity, RatingModel> {
        return RatingEntityMapper()
    }

    @Provides
    @Singleton
    fun provideNotificationEntityMapper(baseUrl: BaseUrl): DomainMapper<NotificationEntity, NotificationModel> {
        return NotificationEntityMapper(baseUrl = baseUrl)
    }

}
