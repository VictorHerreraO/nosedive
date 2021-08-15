package com.soyvictorherrera.nosedive.data.repository.sharingCode

import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeDataSource
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

class SharingCodeRepositoryImpl(
    private val sharingCodeSource: SharingCodeDataSource
) : SharingCodeRepository {

    override suspend fun saveSharingCode(entity: SharingCodeEntity): Flow<Result<SharingCodeEntity>> {
        return sharingCodeSource.saveSharingCode(entity = entity)
    }

    override suspend fun getSharingCode(
        sharingCodeId: String
    ): Flow<Result<SharingCodeEntity>> {
        return sharingCodeSource.getSharingCode(sharingCodeId = sharingCodeId)
    }

    override suspend fun deleteSharingCode(sharingCodeId: String): Flow<Result<Unit>> {
        return sharingCodeSource.deleteSharingCode(sharingCodeId = sharingCodeId)
    }

}
