package com.english.vocab.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.english.vocab.MainActivity
import com.koai.vocabvoyage.R
import com.english.vocab.utils.AppConfig

class MyMessaging : FirebaseMessagingService() {
    companion object {
        const val CHANNEL_ID = "app_global"
        const val BODY = "body"
        const val TITLE = "title"
        const val KOE_NOTIFICATION = "koe_notification"
        const val KEY_NOTIFICATION = "key_notification"
        const val KEY_NOTIFICATION_ID = "key_notification_id"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (!AppConfig.isForeground) {
            showNotification(message)
        } else {
            showNotifyOnForeGround(data = message)
        }
    }

    private fun showNotification(data: RemoteMessage) {
        val intent =
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle(data.data[TITLE] ?: resources.getString(R.string.app_name))
                .setContentText(data.data[BODY])
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVibrate(LongArray(0))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColorized(true)
                .setColor(Color.parseColor("#3D90FA"))

        createNotificationChannel()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    this@MyMessaging,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1403, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotifyOnForeGround(data: RemoteMessage) {
        val activityIntent = Intent(KOE_NOTIFICATION)
        activityIntent.apply {
            putExtra(KEY_NOTIFICATION, data)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(activityIntent)
    }

}
