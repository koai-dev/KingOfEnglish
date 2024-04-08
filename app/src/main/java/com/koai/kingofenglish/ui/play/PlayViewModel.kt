package com.koai.kingofenglish.ui.play

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koai.base.network.ResponseStatus
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.domain.models.Question
import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.usecase.GetQuestionUseCase
import com.koai.kingofenglish.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PlayViewModel(
    private val getQuestionUseCase: GetQuestionUseCase,
    private val sharePreference: SharePreference
) : ViewModel() {
    private val _question = MutableLiveData<ResponseStatus<Response<Question>>>()
    val questionLiveData: LiveData<ResponseStatus<Response<Question>>?> = _question
    private val _timerCountdown = MutableLiveData<Int>()
    val timerCountdown: LiveData<Int> = _timerCountdown
    private var jobTimer: Job? = null
    private var currentLevel = 0
    private var question: Question? = null
    private var timer: CountDownTimer? = null

    private var _currentPoint = MutableLiveData<Int>()
    var currentPoint: LiveData<Int> = _currentPoint
    fun getQuestionByLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            getQuestionUseCase.execute(currentLevel)
                .onStart {
                    _question.postValue(ResponseStatus.Loading)
                }.catch {
                    _question.postValue(ResponseStatus.Error(message = it.message ?: ""))
                }.collect {
                    currentLevel += 1
                    _question.postValue(ResponseStatus.Success(it))
                }
        }
    }

    fun getCurrentState() {
        currentLevel = sharePreference.getIntPref(Constants.CURRENT_QUESTION)
        var savePoints = sharePreference.getIntPref(Constants.CURRENT_POINTS)
        if (savePoints <= 0) {
            savePoints = 0
        }
        _currentPoint.postValue(savePoints)
        if (currentLevel <= 1) {
            currentLevel = 1
        }

    }

    fun countdownTimeAnswer() {
        viewModelScope.launch {
            timer?.cancel()
            jobTimer?.cancelAndJoin()
            jobTimer = viewModelScope.launch {
                timer = object : CountDownTimer(61000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        _timerCountdown.postValue((millisUntilFinished / 1000).toInt())
                    }

                    override fun onFinish() {
                        _timerCountdown.postValue(0)
                    }

                }
                timer?.start()
            }
        }
    }

    fun calculateCurrentPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            val point = (timerCountdown.value ?: 0) * 10 * (question?.levelQuestion ?: 1)
            val currentPoints = point + (currentPoint.value ?: 0)
            _currentPoint.postValue(currentPoints)
        }
    }

    fun setQuestion(question: Question?) {
        this.question = question
    }

    fun onDestroy() {
        currentLevel-=1
        sharePreference.setIntPref(Constants.CURRENT_QUESTION, currentLevel)
        currentPoint.value?.let { sharePreference.setIntPref(Constants.CURRENT_POINTS, it) }
    }
}