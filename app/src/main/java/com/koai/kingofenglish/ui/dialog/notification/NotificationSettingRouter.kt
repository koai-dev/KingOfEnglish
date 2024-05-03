package com.koai.kingofenglish.ui.dialog.notification

import android.app.Activity
import com.koai.base.main.action.router.BaseRouter

interface NotificationSettingRouter : BaseRouter{
    fun gotoNotificationSetting(activity: Activity)
    fun gotoTutorial()
}