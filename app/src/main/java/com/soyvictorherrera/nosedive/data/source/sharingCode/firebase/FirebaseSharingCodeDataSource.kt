package com.soyvictorherrera.nosedive.data.source.sharingCode.firebase

import com.google.firebase.database.DatabaseReference
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeDataSource
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await


class FirebaseSharingCodeDataSource(
    private val sharingCodes: DatabaseReference
) : SharingCodeDataSource {

    override suspend fun saveSharingCode(entity: SharingCodeEntity): Flow<Result<SharingCodeEntity>> {
        return try {
            val publicCode = entity.code
            val userId = entity.userId
            if (publicCode.isNullOrEmpty() || userId.isNullOrEmpty()) {
                throw IllegalStateException("{publicCode} and {userId} must not be empty")
            }

            val entityId: String = entity.id ?: publicCode

            sharingCodes.child(entityId).setValue(entity).await()

            flowOf(Result.Success(data = entity))
        } catch (ex: Exception) {
            flowOf(Result.Error(exception = ex))
        }
    }

    override suspend fun getSharingCode(
        sharingCodeId: String
    ): Flow<Result<SharingCodeEntity>> {
        return try {
            if (sharingCodeId.isEmpty()) {
                throw IllegalArgumentException("{sharingCodeId} must not be empty")
            }

            sharingCodes.child(sharingCodeId)
                .get()
                .await()
                .getValue(SharingCodeEntity::class.java)
                ?.let { entity ->
                    flowOf(Result.Success(data = entity))
                } ?: throw RuntimeException("no sharingCode found with id {$sharingCodeId}")
        } catch (ex: Exception) {
            flowOf(Result.Error(exception = ex))
        }
    }

    override suspend fun deleteSharingCode(sharingCodeId: String): Flow<Result<Unit>> {
        return try {
            if (sharingCodeId.isEmpty()) {
                throw IllegalArgumentException("{sharingCodeId} must not be empty")
            }

            sharingCodes.child(sharingCodeId).removeValue().await()

            flowOf(Result.Success(data = Unit))
        } catch (ex: Exception) {
            flowOf(Result.Error(exception = ex))
        }
    }

}
