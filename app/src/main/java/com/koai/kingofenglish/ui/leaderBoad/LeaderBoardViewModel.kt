package com.koai.kingofenglish.ui.leaderBoad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.domain.usecase.GetTopRankUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LeaderBoardViewModel(private val getTopRankUseCase: GetTopRankUseCase): ViewModel() {
    private val _leaders = MutableLiveData<ResponseStatus<Response<List<User>>>>()
    val leaders: LiveData<ResponseStatus<Response<List<User>>>> = _leaders

    fun getTopLeaders(){
        viewModelScope.launch(Dispatchers.IO) {
            getTopRankUseCase.execute().onStart {
                _leaders.postValue(ResponseStatus.Loading)
            }.catch {
                _leaders.postValue(ResponseStatus.Error(it.message.toString()))
            }.collect{
                _leaders.postValue(ResponseStatus.Success(it))
            }
        }
    }
}