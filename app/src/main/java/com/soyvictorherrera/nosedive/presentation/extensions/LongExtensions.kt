package com.soyvictorherrera.nosedive.presentation.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(
    zoneId: ZoneId = ZoneId.systemDefault()
) : LocalDateTime = Instant.ofEpochMilli(this)
    .atZone(zoneId)
    .toLocalDateTime()

