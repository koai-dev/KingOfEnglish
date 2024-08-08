package com.english.vocab.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.english.vocab.domain.models.Background
import com.english.vocab.domain.models.Response
import com.english.vocab.domain.usecase.BackgroundUseCase
import com.english.vocab.utils.Constants
import com.koai.base.network.ResponseStatus
import com.koai.base.utils.SharePreference
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

    fun saveSetting(url: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            sharePreference.setStringPref(Constants.BACKGROUND_URL, url)
        }
    }
}
