package com.soyvictorherrera.nosedive.domain.usecase.sharing

import com.soyvictorherrera.nosedive.data.repository.sharingCode.SharingCodeRepository
import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.domain.mapper.DomainMapper
import com.soyvictorherrera.nosedive.domain.model.SharingCodeModel
import com.soyvictorherrera.nosedive.domain.usecase.BaseUseCase
import com.soyvictorherrera.nosedive.util.Result
import com.soyvictorherrera.nosedive.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetTextSharingCodeUseCase(
    private val sharingCodeRepository: SharingCodeRepository,
    private val sharingCodeEntityMapper: DomainMapper<SharingCodeEntity, SharingCodeModel>
) : BaseUseCase<Result<SharingCodeModel>>() {

    var publicCode = ""

    override suspend fun buildFlow(): Flow<Result<SharingCodeModel>> {
        val code = publicCode
        return if (publicCode.isEmpty()) {
            flowOf(Result.Error(exception = IllegalArgumentException("[publicCode] must not be empty")))
        } else {
            sharingCodeRepository.getSharingCode(publicSharingCode = code)
                .map { result ->
                    result.map(sharingCodeEntityMapper::toDomainModel)
                }
        }
    }
}
