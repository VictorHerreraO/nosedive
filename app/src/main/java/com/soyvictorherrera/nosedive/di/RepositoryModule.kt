package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.friend.FriendRepository
import com.soyvictorherrera.nosedive.data.repository.friend.FriendRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.notification.NotificationRepository
import com.soyvictorherrera.nosedive.data.repository.notification.NotificationRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.rating.RatingRepository
import com.soyvictorherrera.nosedive.data.repository.rating.RatingRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.sharingCode.SharingCodeRepository
import com.soyvictorherrera.nosedive.data.repository.sharingCode.SharingCodeRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.token.TokenRepository
import com.soyvictorherrera.nosedive.data.repository.token.TokenRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepositoryImpl
import com.soyvictorherrera.nosedive.data.repository.userStats.UserStatsRepository
import com.soyvictorherrera.nosedive.data.repository.userStats.UserStatsRepositoryImpl
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.friend.FriendDataSource
import com.soyvictorherrera.nosedive.data.source.notification.NotificationDataSource
import com.soyvictorherrera.nosedive.data.source.rating.RatingDataSource
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeDataSource
import com.soyvictorherrera.nosedive.data.source.token.TokenDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(authSource: AuthenticationDataSource): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authDataSource = authSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource = userDataSource)
    }

    @Provides
    @Singleton
    fun provideSharingCodeRepository(sharingCodeSource: SharingCodeDataSource): SharingCodeRepository {
        return SharingCodeRepositoryImpl(sharingCodeSource = sharingCodeSource)
    }

    @Provides
    @Singleton
    fun provideUserStatsRepository(userStatsDataSource: UserStatsDataSource): UserStatsRepository {
        return UserStatsRepositoryImpl(userStatsDataSource = userStatsDataSource)
    }

    @Provides
    @Singleton
    fun provideFriendRepository(friendDataSource: FriendDataSource): FriendRepository {
        return FriendRepositoryImpl(friendDataSource = friendDataSource)
    }

    @Provides
    @Singleton
    fun provideRatingRepository(ratingDataSource: RatingDataSource): RatingRepository {
        return RatingRepositoryImpl(ratingDataSource = ratingDataSource)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationDataSource: NotificationDataSource): NotificationRepository {
        return NotificationRepositoryImpl(notificationDataSource = notificationDataSource)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(tokenDataSource: TokenDataSource): TokenRepository {
        return TokenRepositoryImpl(tokenDataSource = tokenDataSource)
    }

}
