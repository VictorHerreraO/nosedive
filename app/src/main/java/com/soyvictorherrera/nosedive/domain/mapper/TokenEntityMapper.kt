package com.soyvictorherrera.nosedive.domain.mapper

import com.soyvictorherrera.nosedive.data.source.token.TokenEntity
import com.soyvictorherrera.nosedive.domain.model.TokenModel

class TokenEntityMapper : DomainMapper<TokenEntity, TokenModel>() {

    override fun toDomainModel(value: TokenEntity): TokenModel = with(value) {
        return TokenModel(
            id = id,
            string = string!!,
            registration = registration
        )
    }

    override fun fromDomainModel(model: TokenModel): TokenEntity = with(model) {
        return TokenEntity(
            id = id,
            string = string,
            registration = registration
        )
    }
}
