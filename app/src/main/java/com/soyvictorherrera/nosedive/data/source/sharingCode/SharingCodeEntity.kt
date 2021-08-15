package com.soyvictorherrera.nosedive.data.source.sharingCode

import com.google.firebase.database.Exclude

data class SharingCodeEntity(
    @get:Exclude var id: String? = null,
    @get:Exclude var code: String? = null,
    var userId: String? = null
)
