package com.soyvictorherrera.nosedive.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.soyvictorherrera.nosedive.R
import javax.inject.Inject

class NotificationUtil @Inject constructor(
    private val application: Application
) {
    object ChannelId {
        const val RATINGS = "id_ratings"
        const val PROFILE = "id_profile"
    }

    object NotificationId {
        const val NEW_RATING = 10101
        const val NEW_FOLLOW = 10102
    }

    @RequiresApi(Build.VERSION_CODES.O)
    data class ChannelInfo(
        val id: String,
        val name: String,
        val description: String,
        val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    )

    /**
     * Canales conocidos para las notificaciones de la app
     */
    private val channels: List<ChannelInfo> = with(application) {
        return@with if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            emptyList()
        } else listOf(
            ChannelInfo(
                id = ChannelId.RATINGS,
                name = getString(R.string.notification_channel_ratings_title),
                description = getString(R.string.notification_channel_ratings_description),
                importance = NotificationManager.IMPORTANCE_HIGH
            ),
            ChannelInfo(
                id = ChannelId.PROFILE,
                name = getString(R.string.notification_channel_profile_title),
                description = getString(R.string.notification_channel_profile_description),
                importance = NotificationManager.IMPORTANCE_DEFAULT
            )
        )
    }

    init {
        channels.forEach(this::createNotificationChannel)
    }

    fun displayNewRatingNotification(): Unit = with(application) {
        NotificationCompat.Builder(this, ChannelId.RATINGS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(
                "Alguien te ha calificado"
            )
            .setContentText("Entra a tu app para saber más detalles")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
            .let { notification ->
                NotificationManagerCompat.from(this).notify(
                    NotificationId.NEW_RATING,
                    notification
                )
            }
    }

    private fun createNotificationChannel(info: ChannelInfo): Unit = with(info) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val notificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(id, name, importance).apply {
            description = info.description
        }

        notificationManager.createNotificationChannel(channel)
    }


}
