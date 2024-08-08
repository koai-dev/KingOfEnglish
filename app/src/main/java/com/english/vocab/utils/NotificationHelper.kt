package com.english.vocab.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    private const val KEY_APP_PACKAGE_NAME = "app_package"
    private const val KEY_APP_UID = "app_uid"

    fun areNotificationsEnabled(context: Context): Boolean =
        NotificationManagerCompat.from(context)
            .areNotificationsEnabled()

    fun requestAllowNotifyFromSetting(activity: Activity) {
        try {
            val intent = Intent()
            if (!NotificationManagerCompat.from(activity)
                    .areNotificationsEnabled()
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                } else {
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                }
            }
            intent.putExtra(KEY_APP_PACKAGE_NAME, activity.packageName)
            intent.putExtra(KEY_APP_UID, activity.applicationInfo.uid)
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
