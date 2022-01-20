package com.soyvictorherrera.nosedive.data.source.token

import com.soyvictorherrera.nosedive.util.Result

interface TokenDataSource {

    suspend fun save(userId: String, token: TokenEntity): Result<Unit>

}
