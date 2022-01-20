package com.soyvictorherrera.nosedive.data.repository.token

import com.soyvictorherrera.nosedive.data.source.token.TokenDataSource
import com.soyvictorherrera.nosedive.data.source.token.TokenEntity
import com.soyvictorherrera.nosedive.util.Result

class TokenRepositoryImpl(
    private val tokenDataSource: TokenDataSource
) : TokenRepository {

    override suspend fun saveToken(userId: String, token: TokenEntity): Result<Unit> {
        return tokenDataSource.save(userId, token)
    }

}
