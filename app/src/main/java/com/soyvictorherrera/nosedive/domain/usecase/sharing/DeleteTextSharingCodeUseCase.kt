package com.soyvictorherrera.nosedive.domain.usecase.sharing

import com.soyvictorherrera.nosedive.data.repository.sharingCode.SharingCodeRepository
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import kotlinx.coroutines.flow.Flow

class DeleteTextSharingCodeUseCase(
    private val sharingCodeRepository: SharingCodeRepository
) : BaseUseCase<Result<Unit>>() {

    var publicSharingCode = ""

    override suspend fun buildFlow(): Flow<Result<Unit>> {
        return sharingCodeRepository.deleteSharingCode(
            publicSharingCode = publicSharingCode
        )
    }

}
