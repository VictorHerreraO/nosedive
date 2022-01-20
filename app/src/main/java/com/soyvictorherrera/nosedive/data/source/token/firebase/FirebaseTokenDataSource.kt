package com.soyvictorherrera.nosedive.data.source.token.firebase

import com.google.firebase.database.DatabaseReference
import com.soyvictorherrera.nosedive.data.source.token.TokenDataSource
import com.soyvictorherrera.nosedive.data.source.token.TokenEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.tasks.await

class FirebaseTokenDataSource(
    private val tokens: DatabaseReference
) : TokenDataSource {

    override suspend fun save(userId: String, token: TokenEntity): Result<Unit> {
        return when {
            userId.isEmpty() -> Result.Error(
                IllegalArgumentException("[userId] must not be empty")
            )
            token.string.isNullOrEmpty() -> Result.Error(
                IllegalArgumentException("[token.string] must not be null or empty")
            )
            else -> try {
                tokens.child(userId)
                    .push()
                    .setValue(token)
                    .await()
                Result.Success(Unit)
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }
    }
}
