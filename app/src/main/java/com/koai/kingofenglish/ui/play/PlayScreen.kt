package com.koai.kingofenglish.ui.play

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenPlayBinding
import com.koai.kingofenglish.ui.play.widget.Letter
import com.koai.kingofenglish.ui.play.widget.WordView

class PlayScreen : BaseScreen<ScreenPlayBinding, BaseRouter, MainNavigator>(R.layout.screen_play) {

    private val viewModel: PlayViewModel by screenViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", PlayScreen::class.simpleName.toString())
    }

    override fun initView(savedInstanceState: Bundle?, binding: ScreenPlayBinding) {
        Log.d("initView", PlayScreen::class.simpleName.toString())
        viewModel.getCurrentState()
        setupUI()
        getData()
        onAction()
        observer()
    }

    private fun setupUI() {
        with(binding) {
            wordResult.action = object : WordView.Action {
                override fun onClick(position: Int, data: Letter, code: Int) {
                    try {
                        if (data.letter.isNotBlank()) {
                            val currentResultList =
                                wordResult.getAdapter().currentList.toMutableList()
                            currentResultList[position] = Letter(data.letter, isSelected = true)
                            wordResult.submitList(currentResultList)

                            val currentList = wordQuestion.getAdapter().currentList.toMutableList()
                            val nextLetterIndex =
                                currentList.indexOfFirst { letter -> letter.letter == data.letter && letter.isSelected }
                            currentList[nextLetterIndex] = data
                            wordQuestion.submitList(currentList)
                            showCurrentAnswer(currentResultList)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            wordQuestion.action = object : WordView.Action {
                override fun onClick(position: Int, data: Letter, code: Int) {
                    try {
                        if (data.letter.isNotBlank()) {
                            val currentList = wordResult.getAdapter().currentList.toMutableList()
                            val nextLetterIndex =
                                currentList.indexOfFirst { letter -> letter.isSelected }
                            currentList[nextLetterIndex] = data
                            wordResult.submitList(currentList)

                            val currentQuestionList =
                                wordQuestion.getAdapter().currentList.toMutableList()
                            currentQuestionList[position] = Letter(data.letter, isSelected = true)
                            wordQuestion.submitList(currentQuestionList)

                            showCurrentAnswer(currentList)
                            if (currentList.firstOrNull { letter -> letter.isSelected } == null) {
                                if (checkValidAnswer(currentList.toMutableList())) {
                                    Toast.makeText(activity, "Right", Toast.LENGTH_SHORT).show()
                                    viewModel.calculateCurrentPoint()
                                    getData()
                                } else {
                                    Toast.makeText(activity, "Wrong", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun showCurrentAnswer(listAnswer: List<Letter>) {
        var answer = ""
        listAnswer.forEach { letter ->
            answer += if (!letter.isSelected) {
                letter.letter
            }else{
                " "
            }
        }
        binding.answer = answer.trim()
    }

    private fun checkValidAnswer(listAnswer: List<Letter>): Boolean {
        val responseQuestion = viewModel.questionLiveData.value
        if (responseQuestion is ResponseStatus.Success) {
            val validAnswer = responseQuestion.data.data?.answerRight
            var answer = ""
            listAnswer.forEach { letter ->
                answer += letter.letter
            }
            return answer.lowercase() == validAnswer?.lowercase()
        }
        return false
    }

    private fun getData() {
        viewModel.getQuestionByLevel()
    }

    private fun observer() {
        viewModel.questionLiveData.observe(this) { question ->
            question?.let {
                if (it is ResponseStatus.Success) {
                    viewModel.countdownTimeAnswer()
                    viewModel.setQuestion(it.data.data)
                    binding.question = it.data.data
                    binding.answer = null
                    val letters =
                        it.data.data?.answers?.map { txt -> txt?.let { Letter(txt, false) } }
                    val result = it.data.data?.answers?.map { Letter(isSelected = true) }
                    letters?.let {
                        binding.wordQuestion.submitList(letters)
                    }
                    result?.let {
                        binding.wordResult.submitList(result)
                    }
                }
            }
        }

        viewModel.timerCountdown.observe(this){
            binding.txtTimer.text = it.toString()
        }

        viewModel.currentPoint.observe(this){
            binding.currentPoint = it
        }
    }

    private fun onAction() {
        binding.btnPause.setClickableWithScale {

        }

        binding.btnHome.setClickableWithScale {
            router?.onPopScreen()
        }

        binding.btnTip.setClickableWithScale {

        }
    }

    override fun onStop() {
        viewModel.onDestroy()
        super.onStop()
    }

    override val navigator: MainNavigator by navigatorViewModel()
}