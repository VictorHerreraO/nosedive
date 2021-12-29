package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.sharingCode.SharingCodeRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.repository.userScore.UserScoreRepository
import com.soyvictorherrera.nosedive.data.repository.userStats.UserStatsRepository
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.data.source.userScore.UserScoreEntity
import com.soyvictorherrera.nosedive.data.source.userStats.UserStatsEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.SharingCodeModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserScoreModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.domain.usecase.authentication.SignInUseCase
import com.soyvictorherrera.nosedive.domain.usecase.authentication.SignUpUseCase
import com.soyvictorherrera.nosedive.domain.usecase.sharing.*
import com.soyvictorherrera.nosedive.domain.usecase.user.*
import com.soyvictorherrera.nosedive.util.FileUtil
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository,
        preferenceUtil: PreferenceUtil
    ): SignInUseCase {
        return SignInUseCase(
            authRepository = authRepository,
            userRepository = userRepository,
            preferences = preferenceUtil
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository,
        preferenceUtil: PreferenceUtil
    ): SignUpUseCase {
        return SignUpUseCase(
            authRepository = authRepository,
            userRepository = userRepository,
            preferences = preferenceUtil
        )
    }

    @Provides
    @ViewModelScoped
    fun provideObserveCurrentUserUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository,
        userEntityMapper: DomainMapper<UserEntity, UserModel>
    ): ObserveCurrentUserUseCase {
        return ObserveCurrentUserUseCase(
            authRepository = authRepository,
            userRepository = userRepository,
            userEntityMapper = userEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateProfilePhotoUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository,
        fileUtil: FileUtil
    ): UpdateUserPhotoUseCase {
        return UpdateUserPhotoUseCase(
            authRepository = authRepository,
            userRepository = userRepository,
            fileUtil = fileUtil
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateUserPasswordUseCase(
        authRepository: AuthenticationRepository
    ): UpdateUserPasswordUseCase {
        return UpdateUserPasswordUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGenerateQrCodeUseCase(
        fileUtil: FileUtil
    ): GenerateQrCodeUseCase {
        return GenerateQrCodeUseCase(
            fileUtil = fileUtil
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGenerateTextSharingCodeUseCase(
        sharingCodeRepository: SharingCodeRepository,
        sharingCodeEntityMapper: DomainMapper<SharingCodeEntity, SharingCodeModel>
    ): GenerateTextSharingCodeUseCase {
        return GenerateTextSharingCodeUseCase(
            sharingCodeRepository = sharingCodeRepository,
            sharingCodeEntityMapper = sharingCodeEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTextSharingCodeUseCase(
        sharingCodeRepository: SharingCodeRepository
    ): DeleteTextSharingCodeUseCase {
        return DeleteTextSharingCodeUseCase(
            sharingCodeRepository = sharingCodeRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideReadQrCodeUseCase(): ReadQrCodeUseCase {
        return ReadQrCodeUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideGetTextSharingCodeUseCase(
        sharingCodeRepository: SharingCodeRepository,
        sharingCodeEntityMapper: DomainMapper<SharingCodeEntity, SharingCodeModel>
    ): GetTextSharingCodeUseCase {
        return GetTextSharingCodeUseCase(
            sharingCodeRepository = sharingCodeRepository,
            sharingCodeEntityMapper = sharingCodeEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideObserveUserProfileUseCase(
        userRepository: UserRepository,
        userEntityMapper: DomainMapper<UserEntity, UserModel>
    ): ObserveUserProfileUseCase {
        return ObserveUserProfileUseCase(
            userRepository = userRepository,
            userEntityMapper = userEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideObserveUserStatsUseCase(
        statsRepository: UserStatsRepository,
        statsEntityMapper: DomainMapper<UserStatsEntity, UserStatsModel>
    ): ObserveUserStatsUseCase {
        return ObserveUserStatsUseCase(
            statsRepository = statsRepository,
            statsEntityMapper = statsEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideObserveUserScoreUseCase(
        scoreRepository: UserScoreRepository,
        scoreEntityMapper: DomainMapper<UserScoreEntity, UserScoreModel>
    ): ObserveUserScoreUseCase {
        return ObserveUserScoreUseCase(
            scoreRepository,
            scoreEntityMapper
        )
    }

}
