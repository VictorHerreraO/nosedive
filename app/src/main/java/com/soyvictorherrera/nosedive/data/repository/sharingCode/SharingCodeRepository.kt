package com.soyvictorherrera.nosedive.data.repository.sharingCode

import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface SharingCodeRepository {

    suspend fun saveSharingCode(entity: SharingCodeEntity): Flow<Result<SharingCodeEntity>>

    suspend fun getSharingCode(sharingCodeId: String): Flow<Result<SharingCodeEntity>>

    suspend fun deleteSharingCode(sharingCodeId: String): Flow<Result<Unit>>

}
