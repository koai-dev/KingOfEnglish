package com.lpb.analytic

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.lpb.analytic.event.Constants
import com.lpb.analytic.event.cleanValue
import com.lpb.analytic.event.convertToDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

object FALogging {
    private var userName: String? = null
    private var locale: String? = null
    fun setUserName(username: String) {
        this.userName = username
    }

    fun setLocaleTracking(locale: String) {
        this.locale = locale
    }

    private fun log(eventName: String, screenName: String? = null, bundle: Bundle = Bundle()) {
        runBlocking(Dispatchers.IO) {
            if (BuildConfig.DEBUG) {
                Log.d(
                    "FA Logging: ",
                    "${eventName.cleanValue()} - ${screenName.cleanValue()}"
                )
            }
            bundle.apply {
                putString(Constants.ACTION_VIEW, screenName.cleanValue())
                screenName?.let { putString(Constants.SCREEN_NAME, screenName.cleanValue()) }
                putString(Constants.TIME_STAMP, System.currentTimeMillis().convertToDateTime())
                userName?.let { putString(Constants.USER_NAME, userName) }
                locale?.let { putString(Constants.LOCALE, locale) }
            }
            Firebase.analytics.logEvent(eventName, bundle)
            logCrashlytics(eventName, bundle)
        }
    }

    private fun logCrashlytics(key: String, bundle: Bundle?) {
        bundle?.let { messages ->
            messages.keySet().forEach { key ->
                Firebase.crashlytics.log(key + " : " + bundle.getString(key))
            }
        }
        Firebase.crashlytics.recordException(Throwable(message = key))
    }

    fun logScreen(screenName: String) {
        log(screenName)
    }

    fun logAction(screenName: String, actionName: String) {
        log(actionName, screenName)
    }
}