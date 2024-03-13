package com.koai.kingofenglish.network

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionViewModel() : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private val _questionStateFlow = MutableStateFlow<DataApi?>(null)
    val questionStateFlow: StateFlow<DataApi?> get() = _questionStateFlow

    fun getQuestionByLevel(level: Int,context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val questionApi = ApiController().getService(context)
            if (questionApi != null) {
                val call = questionApi.getQuestion(level)
                call.enqueue(object : Callback<DataApi> {
                    override fun onResponse(call: Call<DataApi>, response: Response<DataApi>) {
                        if (response.isSuccessful) {
                            val questionResponse = response.body()
                            _questionStateFlow.value = questionResponse
                        }
                    }

                    override fun onFailure(call: Call<DataApi>, t: Throwable) {
                    }
                })
            }
        }
    }
}