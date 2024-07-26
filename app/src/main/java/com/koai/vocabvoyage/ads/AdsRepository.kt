package com.koai.vocabvoyage.ads

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAds()

    fun showAdsOneTime(
        activity: Activity,
        action: AdmobUtils.Action? = null,
    )

    fun scheduleShowAds(activity: Activity): Flow<Unit>
}
