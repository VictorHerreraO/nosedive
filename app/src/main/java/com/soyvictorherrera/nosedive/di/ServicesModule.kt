package com.soyvictorherrera.nosedive.di

import com.soyvictorherrera.nosedive.data.repository.token.TokenRepository
import com.soyvictorherrera.nosedive.data.source.token.TokenEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.TokenModel
import com.soyvictorherrera.nosedive.domain.usecase.token.AddUserTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
class ServicesModule {

    @Provides
    @ServiceScoped
    fun provideAddUserTokenUseCase(
        tokenRepository: TokenRepository,
        tokenMapper: DomainMapper<TokenEntity, TokenModel>
    ): AddUserTokenUseCase {
        return AddUserTokenUseCase(
            tokenRepository = tokenRepository,
            tokenMapper = tokenMapper
        )
    }

}
