package com.soyvictorherrera.nosedive.domain.model

class SharingCodeModel(
    var id: String? = null,
    var publicCode: String,
    var userId: String
) {
    companion object {
        const val LENGTH = 6
    }
}
