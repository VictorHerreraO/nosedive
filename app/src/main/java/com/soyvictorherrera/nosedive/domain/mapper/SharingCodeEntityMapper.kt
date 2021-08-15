package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.sharingCode.SharingCodeEntity
import com.soyvictorherrera.nosedive.domain.model.SharingCodeModel

class SharingCodeEntityMapper : DomainMapper<SharingCodeEntity, SharingCodeModel>() {

    /* Throw null pointer exception if field is null */
    override fun toDomainModel(value: SharingCodeEntity): SharingCodeModel = with(value) {
        SharingCodeModel(
            id = id,
            publicCode = code!!,
            userId = userId!!
        )
    }

    override fun fromDomainModel(model: SharingCodeModel): SharingCodeEntity = with(model) {
        SharingCodeEntity(
            id = id,
            code = publicCode,
            userId = userId
        )
    }

}