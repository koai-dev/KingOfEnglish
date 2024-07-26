package com.lpb.analytic

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.lpb.analytic.event.Constants
import com.lpb.analytic.event.cleanValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import com.lpb.analytic.BuildConfig

object FALogging {
    private var userName: String? = null
    fun setCurrentUser(userName: String?) {
        this.userName = userName
        Firebase.analytics.setUserId(FALogging.userName)
    }

    private fun log(screenName: String, actionName: String? = null, bundle: Bundle? = Bundle()) {
        runBlocking(Dispatchers.IO) {
            if (BuildConfig.DEBUG) {
                Log.d(
                    "FA Logging: ",
                    "$userName - ${screenName.cleanValue()} - ${actionName.cleanValue()}"
                )
            }
            Firebase.analytics.logEvent(Constants.SCREEN_NAME, bundle?.apply {
                putString(Constants.ACTION_VIEW, screenName.cleanValue())
                actionName?.let { putString(Constants.ACTION_NAME, actionName.cleanValue()) }
                userName?.let { putString(Constants.USER_NAME, userName) }
            })
        }
    }

    fun logScreen(screenName: String) {
        log(screenName)
    }

    fun logAction(screenName: String, actionName: String) {
        log(screenName, actionName)
    }
}