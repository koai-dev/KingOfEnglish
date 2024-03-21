package com.koai.kingofenglish.ui.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PlayViewModel(private val service: ApiService) : ViewModel() {
    fun getQuestionByLevel(level: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            service.getQuestion(1)
                .onStart {
                    emit(ResponseStatus.Loading)
                }.catch {
                    emit(ResponseStatus.Error(message = it.message?:""))
                }.collect {
                    if (it is ResponseStatus.Success){
                        it.data is
                    }
                }
        }
    }
}