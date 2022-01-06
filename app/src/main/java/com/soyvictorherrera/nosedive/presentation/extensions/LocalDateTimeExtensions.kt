package com.soyvictorherrera.nosedive.presentation.extensions

import android.content.Context
import com.soyvictorherrera.nosedive.R
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.describeDuration(
    context: Context,
    until: Instant = Instant.now(),
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    val instant = this.atZone(zoneId).toInstant()
    val duration = Duration.between(instant, until)

    return with(context) {
        when {
            duration.toDays() > 1L -> getString(R.string.duration_since_days, duration.toDays())
            duration.toDays() == 1L -> getString(R.string.duration_since_days_singular)
            duration.toHours() > 1L -> getString(R.string.duration_since_hours, duration.toHours())
            duration.toHours() == 1L -> getString(R.string.duration_since_hours_singular)
            duration.toMinutes() > 1L -> getString(R.string.duration_since_minutes, duration.toMinutes())
            duration.toMinutes() == 1L -> getString(R.string.duration_since_minutes_singular)
            else -> getString(R.string.duration_short_ago)
        }
    }
}
