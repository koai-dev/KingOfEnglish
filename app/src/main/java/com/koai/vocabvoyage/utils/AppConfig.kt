/*
 *  Created by Nguyễn Kim Khánh on 09:49, 08/08/2022
 *     dtako.developer@gmail.com
 *     Last modified 09:49, 08/08/2022
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.koai.vocabvoyage.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.koai.vocabvoyage.BuildConfig
import com.koai.vocabvoyage.R

object AppConfig {
    private const val REWARD_ADS = "reward_ads"

    fun initRemote() {
        FirebaseRemoteConfig.getInstance().setDefaultsAsync(R.xml.remote_config_defaults)
        FirebaseRemoteConfig.getInstance().setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(10).build(),
        )
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
    }

    val rewardAds: String
        get() = FirebaseRemoteConfig.getInstance().getString(REWARD_ADS)
            .ifEmpty { BuildConfig.REWARD_ADS }

    var enableSoundEffect = true
    var enableVibrate = true
    var background: String? = null
    var showedWelcomeTitle = false
    var isForeground = false
    var showPopupNotificationSetting = false
}
