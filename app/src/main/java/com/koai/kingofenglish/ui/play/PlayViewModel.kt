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
import kotlinx.coroutines.delay
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
    private var timer: CountDownTimer? = null

    private var question: Question? = null

    private var currentLevel = 0
    private var currentPoint = 0

    private val _currentPointLive = MutableLiveData<Int>()
    val currentPointLive: LiveData<Int> = _currentPointLive
    private val _currentLevelLive = MutableLiveData<Int>()
    val currentLevelLive: LiveData<Int> = _currentLevelLive

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
        currentPoint = sharePreference.getIntPref(Constants.CURRENT_POINTS)
        if (currentPoint <= 0) {
            currentPoint = 0
        }
        _currentPointLive.postValue(currentPoint)
        if (currentLevel <= 1) {
            currentLevel = 1
        }
        _currentLevelLive.postValue(currentLevel)
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

    fun calculateCurrentPoint(pointAdd: Int = 0) {
        var point = pointAdd
        viewModelScope.launch(Dispatchers.IO) {
            if (point > 0) {
                while (point > 0) {
                    currentPoint = 1 + (currentPointLive.value ?: 0)
                    _currentPointLive.postValue(currentPoint)
                    point -= 1
                    delay(5)
                }
            } else {
                point *= -1
                while (point > 0 && currentPoint > 0) {
                    currentPoint = (currentPointLive.value ?: 0) - 1
                    _currentPointLive.postValue(currentPoint)
                    point -= 1
                    delay(5)
                }
            }
        }
    }

    fun getCurrentPointAdd() = (timerCountdown.value ?: 0) * 10 * (question?.levelQuestion ?: 1)

    fun setQuestion(question: Question?) {
        this.question = question
    }

    fun onDestroy() {
        viewModelScope.launch {
            currentLevel -= 1
            sharePreference.setIntPref(Constants.CURRENT_QUESTION, currentLevel)
            sharePreference.setIntPref(Constants.CURRENT_POINTS, currentPoint)
        }
    }
}