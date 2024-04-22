package com.koai.kingofenglish.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.Background
import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.usecase.BackgroundUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CustomThemeViewModel(private val backgroundUseCase: BackgroundUseCase) : ViewModel() {
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
}