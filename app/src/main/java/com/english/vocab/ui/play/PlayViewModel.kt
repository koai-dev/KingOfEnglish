package com.english.vocab.ui.play

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.english.vocab.domain.account.AccountUtils
import com.english.vocab.domain.models.Question
import com.english.vocab.domain.models.Response
import com.english.vocab.domain.usecase.GetQuestionUseCase
import com.english.vocab.domain.usecase.UpdateUserUseCase
import com.english.vocab.service.Socket
import com.english.vocab.utils.Constants
import com.koai.base.network.ResponseStatus
import com.koai.base.utils.SharePreference
import io.ktor.websocket.Frame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PlayViewModel(
    private val getQuestionUseCase: GetQuestionUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val sharePreference: SharePreference,
) : ViewModel() {
    private val _question = MutableLiveData<ResponseStatus<Response<Question>>>()
    val questionLiveData: LiveData<ResponseStatus<Response<Question>>?> = _question

    private val _timerCountdown = MutableLiveData<Int>()
    val timerCountdown: LiveData<Int> = _timerCountdown
    private var jobTimer: Job? = null
    private var timer: CountDownTimer? = null
    private var timeOut = 61000L
    private var isPlaying = false

    var question: Question? = null

    private var _pointAdd = MutableLiveData<Int?>()
    var pointAdd: LiveData<Int?> = _pointAdd

    private val _needUpdateUserInfo = MutableLiveData<Boolean>()
    val needUpdateUserInfo: LiveData<Boolean> = _needUpdateUserInfo

    fun getQuestionByLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            getQuestionUseCase.execute(AccountUtils.user?.currentLevel ?: 0)
                .onStart {
                    timeOut = 61000L
                    _question.postValue(ResponseStatus.Loading)
                }.catch {
                    _question.postValue(ResponseStatus.Error(message = it.message ?: ""))
                }.collect {
                    _question.postValue(ResponseStatus.Success(it))
                }
        }
    }

    fun updateUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            if (AccountUtils.isLogin()) {
                AccountUtils.user?.let { user ->
                    updateUserUseCase.execute(user).collect { userUpdated ->
                        if ((userUpdated.data?.top ?: 1000) < (user.top ?: 1000))
                            {
                                AccountUtils.user?.top = userUpdated.data?.top
                                Socket.session?.send(
                                    Frame.Text(
                                        "\uD83D\uDC49 ${userUpdated.data?.name} reached the top ${userUpdated.data?.top} best players \uD83D\uDE0D ",
                                    ),
                                )
                            }
                    }
                }
            } else {
                updateUserInfoOffline()
            }
        }
    }

    private fun updateUserInfoOffline() {
        sharePreference.setIntPref(
            Constants.CURRENT_POINTS,
            AccountUtils.user?.points ?: 0,
        )
        sharePreference.setIntPref(
            Constants.CURRENT_QUESTION,
            AccountUtils.user?.currentLevel ?: 1,
        )
    }

    private fun countdownTimeAnswer() {
        viewModelScope.launch {
            timer?.cancel()
            jobTimer?.cancelAndJoin()
            jobTimer =
                viewModelScope.launch {
                    timer =
                        object : CountDownTimer(timeOut, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                if (isPlaying) {
                                    timeOut = millisUntilFinished
                                    _timerCountdown.postValue((millisUntilFinished / 1000).toInt())
                                }
                            }

                            override fun onFinish() {
                                if (isPlaying) {
                                    timeOut = 61000L
                                    _timerCountdown.postValue(0)
                                }
                            }
                        }
                    timer?.start()
                }
        }
    }

    fun calculateCurrentPoint(pointAdd: Int = 0) {
        var point = pointAdd
        viewModelScope.launch(Dispatchers.IO) {
            _needUpdateUserInfo.postValue(false)
            if (point > 0) {
                while (point > 0) {
                    AccountUtils.user?.points = AccountUtils.user?.points?.plus(1)
                    point -= 1
                    _pointAdd.postValue(AccountUtils.user?.points)
                    delay(3)
                }
            } else {
                point *= -1
                while (point > 0 && (AccountUtils.user?.points ?: 0) > 0) {
                    AccountUtils.user?.points = AccountUtils.user?.points?.minus(1)
                    point -= 1
                    _pointAdd.postValue(AccountUtils.user?.points)
                    delay(3)
                }
            }
        }.invokeOnCompletion {
            _needUpdateUserInfo.postValue(true)
        }
    }

    fun getCurrentPointAdd() = (timerCountdown.value ?: 0) * 10 * (question?.levelQuestion ?: 1)

    fun resume() {
        isPlaying = true
        countdownTimeAnswer()
    }

    fun pause() {
        isPlaying = false
        viewModelScope.launch {
            timer?.cancel()
            jobTimer?.cancelAndJoin()
        }
    }
}
