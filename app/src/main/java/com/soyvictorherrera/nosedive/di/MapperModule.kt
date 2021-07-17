package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.mapper.UserEntityMapper
import com.soyvictorherrera.nosedive.domain.model.UserModel
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

}
