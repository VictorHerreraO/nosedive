package com.soyvictorherrera.nosedive.data.repository.token

import com.soyvictorherrera.nosedive.data.source.token.TokenEntity
import com.soyvictorherrera.nosedive.util.Result

interface TokenRepository {

    suspend fun saveToken(userId: String, token: TokenEntity): Result<Unit>

}
