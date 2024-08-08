/*
 *  Created by Nguyễn Kim Khánh on 09:49, 08/08/2022
 *     dtako.developer@gmail.com
 *     Last modified 09:49, 08/08/2022
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.english.vocab.utils

import com.english.vocab.BuildConfig
import com.english.vocab.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object AppConfig {
    private const val REWARD_ADS = "reward_ads"
    private const val MIN_VERSION_NAME = "min_version_name"
    private const val DEFAULT_UPDATED_MSG = "default_updated_msg"
    private const val MIN_VERSION_CODE = "min_version_code"

    fun initRemote() {
        FirebaseRemoteConfig.getInstance().setDefaultsAsync(R.xml.remote_config_defaults)
        FirebaseRemoteConfig.getInstance().setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(10).build(),
        )
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
    }

    val rewardAds: String
        get() =
            FirebaseRemoteConfig.getInstance().getString(REWARD_ADS)
                .ifEmpty { BuildConfig.REWARD_ADS }

    val messageUpdate: String
        get() =
            FirebaseRemoteConfig.getInstance().getString(DEFAULT_UPDATED_MSG)
                .ifEmpty { "- Fix bug and optimize app" }

    val versionNameUpdate: String
        get() =
            FirebaseRemoteConfig.getInstance().getString(MIN_VERSION_NAME)
                .ifEmpty { BuildConfig.VERSION_NAME }

    val versionCodeUpdate: Int
        get() = FirebaseRemoteConfig.getInstance().getLong(MIN_VERSION_CODE).toInt()

    var enableSoundEffect = true
    var enableVibrate = true
    var background: String? = null
    var showedWelcomeTitle = false
    var isForeground = false
    var showPopupNotificationSetting = false

    var locale: String? = null
}
