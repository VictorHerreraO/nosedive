package com.soyvictorherrera.nosedive.domain.usecase.token

import com.soyvictorherrera.nosedive.data.repository.token.TokenRepository
import com.soyvictorherrera.nosedive.data.source.token.TokenEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.TokenModel
import com.soyvictorherrera.nosedive.util.Result

class AddUserTokenUseCase(
    private val tokenRepository: TokenRepository,
    private val tokenMapper: DomainMapper<TokenEntity, TokenModel>
) {

    var userId: String? = null
    var token: TokenModel? = null

    suspend fun execute(): Result<Unit> {
        val safeId = userId
        if (safeId.isNullOrEmpty()) return Result.Error(
            IllegalArgumentException("[userId] must not be null or empty")
        )
        val safeToken = token ?: return Result.Error(
            IllegalArgumentException("[token] must not be null or empty")
        )

        return tokenRepository.saveToken(
            userId = safeId,
            token = tokenMapper.fromDomainModel(safeToken)
        )
    }

}
