package com.english.vocab.ui.play

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.english.vocab.R
import com.english.vocab.ads.AdmobUtils
import com.english.vocab.ads.AdsViewModel
import com.english.vocab.databinding.ScreenPlayBinding
import com.english.vocab.domain.account.AccountUtils
import com.english.vocab.ui.play.widget.Letter
import com.english.vocab.ui.play.widget.WordView
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.journeyViewModel
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus
import com.english.vocab.MainNavigator
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.Constants
import com.english.vocab.utils.share

class PlayScreen : BaseScreen<ScreenPlayBinding, PlayRouter, MainNavigator>(R.layout.screen_play) {
    private val viewModel: PlayViewModel by screenViewModel()
    private val adsViewModel: AdsViewModel by journeyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", PlayScreen::class.simpleName.toString())
        setFragmentResultListener(Constants.ADDED_POINTS) { requestKey, bundle ->
            if (requestKey == Constants.ADDED_POINTS) {
                val pointAdd = bundle.getInt(Constants.ADDED_POINTS)
                viewModel.calculateCurrentPoint(pointAdd)
                getData()
            }
        }
        setFragmentResultListener(Constants.WATCH_ADS) { requestKey, bundle ->
            if (requestKey == Constants.WATCH_ADS) {
                if (AdmobUtils.isLoadedAdmob()) {
                    adsViewModel.showAdsOneTime(
                        activity,
                        object : AdmobUtils.Action {
                            override fun onReward() {
                                val pointAdd = bundle.getInt(Constants.ADDED_POINTS)
                                viewModel.calculateCurrentPoint(pointAdd)
                                getData()
                            }
                        },
                    )
                } else {
                    val pointAdd = bundle.getInt(Constants.ADDED_POINTS)
                    viewModel.calculateCurrentPoint(pointAdd)
                    getData()
                }
            }
        }

        setFragmentResultListener(Constants.SHARE) { requestKey, bundle ->
            if (requestKey == Constants.SHARE) {
                val pointAdd = bundle.getInt(Constants.ADDED_POINTS)
                AccountUtils.user?.points = pointAdd + (AccountUtils.user?.points ?: 0)
                binding.user = AccountUtils.user
                binding.shareView.updateUI()
                getData()
                binding.shareView.share(activity, router)
            }
        }
        setFragmentResultListener(Constants.REPLAY) { requestKey, _ ->
            if (requestKey == Constants.REPLAY) {
                getData()
            }
        }
        setFragmentResultListener(Constants.RESUME) { requestKey, _ ->
            if (requestKey == Constants.RESUME) {
                binding.btnPause.gone()
                viewModel.resume()
            }
        }
        setFragmentResultListener(Constants.SHOW_TIP) { requestKey, _ ->
            if (requestKey == Constants.SHOW_TIP) {
                viewModel.question?.thumb?.let { router?.gotoTip(it) }
            }
        }
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenPlayBinding,
    ) {
        Log.d("initView", PlayScreen::class.simpleName.toString())
        binding.user = AccountUtils.user
        setupUI()
        getData()
        onAction()
        observer()
    }

    private fun setupUI() {
        with(binding) {
            wordResult.action =
                object : WordView.Action {
                    override fun onClick(
                        position: Int,
                        data: Letter,
                        code: Int,
                    ) {
                        try {
                            if (data.letter.isNotBlank()) {
                                val currentResultList =
                                    wordResult.getAdapter().currentList.toMutableList()
                                currentResultList[position] = Letter(data.letter, isSelected = true)
                                wordResult.submitList(currentResultList)

                                val currentList =
                                    wordQuestion.getAdapter().currentList.toMutableList()
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

            wordQuestion.action =
                object : WordView.Action {
                    override fun onClick(
                        position: Int,
                        data: Letter,
                        code: Int,
                    ) {
                        try {
                            if (data.letter.isNotBlank()) {
                                val currentList =
                                    wordResult.getAdapter().currentList.toMutableList()
                                val nextLetterIndex =
                                    currentList.indexOfFirst { letter -> letter.isSelected }
                                currentList[nextLetterIndex] = data
                                wordResult.submitList(currentList)

                                val currentQuestionList =
                                    wordQuestion.getAdapter().currentList.toMutableList()
                                currentQuestionList[position] =
                                    Letter(data.letter, isSelected = true)
                                wordQuestion.submitList(currentQuestionList)

                                showCurrentAnswer(currentList)
                                if (currentList.firstOrNull { letter -> letter.isSelected } == null) {
                                    if (checkValidAnswer(currentList.toMutableList())) {
                                        AccountUtils.user?.currentLevel =
                                            AccountUtils.user?.currentLevel?.plus(1)
                                        router?.nextLevel(
                                            viewModel.getCurrentPointAdd(),
                                            viewModel.question?.thumb
                                        )
                                        viewModel.pause()
                                    } else {
                                        viewModel.pointAdd
                                        router?.showWrongToast()
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
            answer +=
                if (!letter.isSelected) {
                    letter.letter
                } else {
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
                    viewModel.resume()
                    it.data.data?.answers?.shuffle()
                    viewModel.question = it.data.data
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

        viewModel.timerCountdown.observe(this) {
            binding.txtTimer.text = it.toString()
            if (it <= 0) {
                viewModel.pause()
                router?.lose()
            }
        }

        viewModel.pointAdd.observe(this) {
            binding.user = AccountUtils.user
        }

        viewModel.needUpdateUserInfo.observe(this) {
            if (it) {
                viewModel.updateUserInfo()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.updateUserInfo()
    }

    private fun onAction() {
        binding.btnPause.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            viewModel.pause()
            router?.onPause()
        }

        binding.btnHome.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.onPopScreen()
        }

        binding.btnTip.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            if (binding.btnPause.visibility == View.VISIBLE) {
                viewModel.pause()
            }
            router?.watchAds()
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
}
