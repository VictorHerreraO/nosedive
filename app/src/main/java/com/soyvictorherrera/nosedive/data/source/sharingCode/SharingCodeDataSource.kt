package com.soyvictorherrera.nosedive.data.source.sharingCode

import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

interface SharingCodeDataSource {

    suspend fun saveSharingCode(entity: SharingCodeEntity): Flow<Result<SharingCodeEntity>>

    suspend fun getSharingCode(publicSharingCode: String): Flow<Result<SharingCodeEntity>>

    suspend fun deleteSharingCode(publicSharingCode: String): Flow<Result<Unit>>

}
