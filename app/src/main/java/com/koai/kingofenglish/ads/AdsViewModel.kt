package com.koai.kingofenglish.ads

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AdsViewModel(private val adsRepository: AdsRepository) : ViewModel() {
    fun getAds()  {
        viewModelScope.launch {
            adsRepository.getAds()
        }
    }

    fun showAdsOneTime(
        activity: Activity,
        action: AdmobUtils.Action? = null,
    )  {
        viewModelScope.launch {
            adsRepository.showAdsOneTime(activity, action)
        }
    }

    fun scheduleShowAds(activity: Activity)  {
        viewModelScope.launch {
            adsRepository.scheduleShowAds(activity).onStart {
                getAds()
            }.collect {
            }
        }
    }
}
