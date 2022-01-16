package com.soyvictorherrera.nosedive.data.source.notification

import com.soyvictorherrera.nosedive.data.source.IdentifiableEntity

data class NotificationEntity(
    override var id: String? = null,
    var date: Long? = null,
    var type: String? = null,
    var who: String? = null,
    var seen: Long? = null,
    var data: Map<String, Any>? = null,
): IdentifiableEntity
