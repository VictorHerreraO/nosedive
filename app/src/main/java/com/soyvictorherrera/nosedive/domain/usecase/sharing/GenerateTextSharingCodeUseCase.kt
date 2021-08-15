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
import org.apache.commons.lang3.RandomStringUtils

class GenerateTextSharingCodeUseCase(
    private val sharingCodeRepository: SharingCodeRepository,
    private val sharingCodeEntityMapper: DomainMapper<SharingCodeEntity, SharingCodeModel>
) : BaseUseCase<Result<SharingCodeModel>>() {

    var userId = ""

    override suspend fun buildFlow(): Flow<Result<SharingCodeModel>> {
        val uId = userId
        if (uId.isEmpty()) {
            return flowOf(Result.Error(exception = IllegalArgumentException("{userId} must not be empty")))
        }

        val publicCode = RandomStringUtils.randomNumeric(6)
        val entity = SharingCodeEntity(
            code = publicCode,
            userId = uId
        )

        return sharingCodeRepository.saveSharingCode(
            entity = entity
        ).map { result ->
            result.map { sharingCodeEntityMapper.toDomainModel(it) }
        }
    }

}
