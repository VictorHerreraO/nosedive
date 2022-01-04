package com.soyvictorherrera.nosedive.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.soyvictorherrera.nosedive.data.source.authentication.AuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.authentication.firebase.FirebaseAuthenticationDataSource
import com.soyvictorherrera.nosedive.data.source.friend.FriendDataSource
import com.soyvictorherrera.nosedive.data.source.friend.firebase.FirebaseFriendDataSource
import com.soyvictorherrera.nosedive.data.source.rating.RatingDataSource
import com.soyvictorherrera.nosedive.data.source.rating.firebase.FirebaseRatingDataSource
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeDataSource
import com.soyvictorherrera.nosedive.data.source.sharingCode.firebase.FirebaseSharingCodeDataSource
import com.soyvictorherrera.nosedive.data.source.user.UserDataSource
import com.soyvictorherrera.nosedive.data.source.user.firebase.FirebaseUserDataSource
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreDataSource
import com.soyvictorherrera.nosedive.data.source.userScore.firebase.FirebaseUserScoreDataSource
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsDataSource
import com.soyvictorherrera.nosedive.data.source.userStats.firebase.FirebaseUserStatsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    private object Names {
        const val UserRef = "userRef"
        const val UserPhotoRef = "userPhotoRef"
        const val SharingCodeRef = "sharingCodeRef"
        const val UserStatsRef = "userStatsRef"
        const val UserScoreRef = "userScoreRef"
        const val FriendRef = "friendRef"
        const val RatingRef = "ratingRef"
    }

    private object FirebasePaths {
        const val User = "user"
        const val UserPhoto = "userPhoto"
        const val SharingCode = "sharingCode"
        const val UserStats = "userStats"
        const val UserScore = "userScore"
        const val Friend = "friend"
        const val Rating = "rating"
    }

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

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
    //endregion

    //region Firebase database references
    @Provides
    @Singleton
    @Named(Names.UserRef)
    fun provideUserDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference(FirebasePaths.User)
    }

    @Provides
    @Singleton
    @Named(Names.UserPhotoRef)
    fun provideUserPhotoStorageReference(storage: FirebaseStorage): StorageReference {
        return storage.getReference(FirebasePaths.UserPhoto)
    }

    @Provides
    @Singleton
    @Named(Names.SharingCodeRef)
    fun provideSharingCodeDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference(FirebasePaths.SharingCode)
    }

    @Provides
    @Singleton
    @Named(Names.UserStatsRef)
    fun provideUserStatsDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference(FirebasePaths.UserStats)
    }

    @Provides
    @Singleton
    @Named(Names.UserScoreRef)
    fun provideUserScoreDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference(FirebasePaths.UserScore)
    }

    @Provides
    @Singleton
    @Named(Names.FriendRef)
    fun provideFriendDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference(FirebasePaths.Friend)
    }

    @Provides
    @Singleton
    @Named(Names.RatingRef)
    fun provideRatingDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference(FirebasePaths.Rating)
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
    fun provideUserDataSource(
        @Named(Names.UserRef) users: DatabaseReference,
        @Named(Names.UserPhotoRef) userPhotos: StorageReference
    ): UserDataSource {
        return FirebaseUserDataSource(
            users = users,
            userPhotos = userPhotos
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseSharingCodeDataSource(
        @Named(Names.SharingCodeRef) sharingCodes: DatabaseReference
    ): SharingCodeDataSource {
        return FirebaseSharingCodeDataSource(
            sharingCodes = sharingCodes
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseUserStatsDataSource(
        @Named(Names.UserStatsRef) stats: DatabaseReference
    ): UserStatsDataSource {
        return FirebaseUserStatsDataSource(
            stats = stats
        )
    }

    @Provides
    @Singleton
    fun provideUserScoreDataSource(
        @Named(Names.UserScoreRef) scores: DatabaseReference
    ): UserScoreDataSource {
        return FirebaseUserScoreDataSource(
            scores = scores
        )
    }

    @Provides
    @Singleton
    fun provideFriendDataSource(
        @Named(Names.FriendRef) friends: DatabaseReference
    ): FriendDataSource {
        return FirebaseFriendDataSource(
            friends = friends
        )
    }

    @Provides
    @Singleton
    fun provideRatingDataSource(
        @Named(Names.RatingRef) ratings: DatabaseReference
    ): RatingDataSource {
        return FirebaseRatingDataSource(
            ratings = ratings
        )
    }
    //endregion

}
