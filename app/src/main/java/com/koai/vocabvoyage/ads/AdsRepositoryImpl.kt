package com.koai.vocabvoyage.ads

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class AdsRepositoryImpl(private val context: Context) : AdsRepository {
    private val minIntervalMillis = 60000L
    private var lastShowAdsTime = 0L

    override fun getAds() {
        AdmobUtils.setAdmob(context)
    }

    override fun showAdsOneTime(
        activity: Activity,
        action: AdmobUtils.Action?,
    ) {
        if (System.currentTimeMillis() - lastShowAdsTime >= minIntervalMillis) {
            AdmobUtils.showAdmob(activity, action)
            lastShowAdsTime = System.currentTimeMillis()
        } else {
            action?.onReward()
        }
    }

    override fun scheduleShowAds(activity: Activity) =
        flow {
            repeat(1000) {
                delay(180000)
                showAdsOneTime(activity)
                emit(Unit)
            }
        }
}
