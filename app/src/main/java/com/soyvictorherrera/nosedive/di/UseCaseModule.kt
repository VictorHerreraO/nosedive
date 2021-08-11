package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.repository.authentication.AuthenticationRepository
import com.soyvictorherrera.nosedive.data.repository.user.UserRepository
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignInUseCase
import com.soyvictorherrera.nosedive.domain.usecase.SignUpUseCase
import com.soyvictorherrera.nosedive.domain.usecase.UpdateProfilePhotoUseCase
import com.soyvictorherrera.nosedive.domain.usecase.sharing.GenerateQrCodeUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.UpdateUserPasswordUseCase
import com.soyvictorherrera.nosedive.util.FileUtil
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
        userRepository: UserRepository
    ): SignInUseCase {
        return SignInUseCase(
            authRepository = authRepository,
            userRepository = userRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        authRepository: AuthenticationRepository,
        userRepository: UserRepository
    ): SignUpUseCase {
        return SignUpUseCase(
            authRepository = authRepository,
            userRepository = userRepository
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
    ): UpdateProfilePhotoUseCase {
        return UpdateProfilePhotoUseCase(
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

}
