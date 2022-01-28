package com.soyvictorherrera.nosedive.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.ui.MainActivity
import javax.inject.Inject

class NotificationUtil @Inject constructor(
    private val application: Application
) {
    object ChannelId {
        const val RATINGS = "id_ratings"
        const val PROFILE = "id_profile"
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

    fun displayNewRatingNotification(
        notificationId: String,
        rating: Int,
        raterId: String,
        raterName: String? = null
    ): Unit = with(application) {
        val pendingIntent: PendingIntent = defaultPendingIntent(
            destination = R.id.friendProfileFragment,
            extras = Bundle().apply {
                putString("user-id", raterId)
            }
        )

        NotificationCompat.Builder(this, ChannelId.RATINGS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(
                if (raterName.isNullOrEmpty()) getString(R.string.notification_rating_title_anon)
                else getString(R.string.notification_rating_title_user, raterName)
            )
            .setContentText(
                if (rating == 1) getString(
                    R.string.notification_rating_content_rating_singular,
                    rating
                ) else getString(R.string.notification_rating_content_rating_plural, rating)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
            .let { notification ->
                NotificationManagerCompat.from(this).notify(
                    notificationId.hashCode(),
                    notification
                )
            }
    }

    fun displayNewFollowerNotification(
        notificationId: String,
        followerName: String? = null
    ): Unit = with(application) {
        val pendingIntent = defaultPendingIntent(
            destination = R.id.notificationFragment
        )
        NotificationCompat.Builder(this, ChannelId.PROFILE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(
                getString(R.string.notification_new_follower_title)
            )
            .setContentText(
                if (followerName.isNullOrEmpty()) getString(R.string.notification_new_follower_content_anon)
                else getString(R.string.notification_new_follower_content_user, followerName)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
            .let { notification ->
                NotificationManagerCompat.from(this).notify(
                    notificationId.hashCode(),
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

    private fun defaultPendingIntent(
        @IdRes destination: Int,
        extras: Bundle? = null
    ): PendingIntent = with(application) {
        return NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(destination)
            .setArguments(extras)
            .createPendingIntent()
    }

}
