/*
 *  Created by Nguyễn Kim Khánh on 09:49, 08/08/2022
 *     dtako.developer@gmail.com
 *     Last modified 09:49, 08/08/2022
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.koai.kingofenglish.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.koai.kingofenglish.R

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

    var enableSoundEffect = true
    var enableVibrate = true
}
