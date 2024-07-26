package com.koai.vocabvoyage.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koai.base.network.ResponseStatus
import com.koai.base.utils.SharePreference
import com.koai.vocabvoyage.domain.models.Background
import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.usecase.BackgroundUseCase
import com.koai.vocabvoyage.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CustomThemeViewModel(private val backgroundUseCase: BackgroundUseCase, private val sharePreference: SharePreference) : ViewModel() {
    private val _backgrounds = MutableLiveData<ResponseStatus<Response<List<Background>>>>()
    val backgrounds: LiveData<ResponseStatus<Response<List<Background>>>> = _backgrounds

    fun getBackgrounds() {
        viewModelScope.launch(Dispatchers.IO) {
            backgroundUseCase.getAll().onStart {
                _backgrounds.postValue(ResponseStatus.Loading)
            }.collect {
                _backgrounds.postValue(ResponseStatus.Success(it))
            }
        }
    }

    fun saveSetting(url: String){
        viewModelScope.launch(Dispatchers.IO) {
            sharePreference.setStringPref(Constants.BACKGROUND_URL, url)
        }
    }
}