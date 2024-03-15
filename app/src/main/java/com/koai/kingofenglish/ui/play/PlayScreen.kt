package com.koai.kingofenglish.ui.play

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenPlayBinding
import com.koai.kingofenglish.network.QuestionViewModel
import com.koai.wordsdk.WordSdk
import java.util.Random


class PlayScreen : BaseScreen<ScreenPlayBinding, PlayRouter, MainNavigator>(R.layout.screen_play) {
    private val viewModel : QuestionViewModel by viewModels()
    private var level = 1
    private var presCounter = 0
    private var maxPresCounter = 0
    private var keys: Array<String> = emptyArray()
    private var textAnswer : String = ""
    private lateinit var countDownTimer: CountDownTimer
    override fun initView(savedInstanceState: Bundle?, binding: ScreenPlayBinding) {
        level = SharePreference.getIntPref(requireContext(),"LEVEL")
        getData()
    }

    private fun getData() {
        binding.txtLevel.text = level.toString()
        textAnswer = arguments?.getString("QUESTION")!!
        setupViews()
    }

    private fun setupViews() {
        maxPresCounter = keys.size
        maxPresCounter = textAnswer.length
        keys = textAnswer.toCharArray().map { it.toString() }.toTypedArray()
        keys = shuffleArray(keys)
        binding.textViewQuestion.text = keys.joinToString("/")
        for (key in keys) {
            addView(binding.layoutQuestion, key, binding.editText, binding.layoutAnswer)
        }
        countDownTimer()
        binding.imgPause.setClickableWithScale {
            router?.dialogPause()
            countDownTimer.cancel()
        }
        binding.imgCup.setClickableWithScale {
            router?.goToLeaderBoard()
            countDownTimer.onFinish()
        }
        binding.ctnBtnHome.setClickableWithScale {
            router?.goToHome()
        }
        binding.ctnBtnTips.setClickableWithScale {
            addTips()
        }
    }

    private fun addTips() {
        
    }

    private fun countDownTimer() {
        val totalTimeInMillis: Long = 61 * 1000

        val intervalInMillis: Long = 1000
        countDownTimer = object : CountDownTimer(totalTimeInMillis, intervalInMillis) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.txtTime.text = secondsRemaining.toString()

            }

            override fun onFinish() {
                binding.txtTime.text = "Done!"
            }
        }

        countDownTimer.start()
    }

    private fun minus5sIncorrect() {
        val currentTimeInMillis = (binding.txtTime.text.toString().toLongOrNull() ?: 0) * 1000
        val newTimeInMillis = currentTimeInMillis - 5000
        if (newTimeInMillis >= 0) {
            countDownTimer.cancel()
            countDownTimer = object : CountDownTimer(newTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    binding.txtTime.text = secondsRemaining.toString()
                    binding.txtTimeMinus.alpha = 1f
                    binding.txtTimeMinus.text = "-5"
                    binding.txtTimeMinus.animate().alpha(0f).setDuration(2000).start()
                }

                override fun onFinish() {
                    binding.txtTime.text = "Done!"
                }
            }
            countDownTimer.start()
        }
    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }


    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    private fun addView(
        viewParent: GridLayout,
        text: String,
        editText: EditText,
        viewParent2: GridLayout
    ) {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.super_corn)
        val textViewQuestion = WordSdk.createWordView(
            requireContext(),
            text,
            32f,
            typeface!!,
            ResourcesCompat.getDrawable(resources, R.drawable.bg_text, null)!!,
            ResourcesCompat.getColor(resources, R.color.white, null)
        )

        val textViewAnswer = WordSdk.createWordView(
            requireContext(),
            "",
            32f,
            typeface,
            ResourcesCompat.getDrawable(resources, R.drawable.bg_empty_text, null)!!,
            ResourcesCompat.getColor(resources, R.color.white, null)
        )
//        textViewQuestion.setOnTouchListener { v, event ->
//            when (event?.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    textViewQuestion.alpha = 0.5f
//                }
//                MotionEvent.ACTION_UP -> {
//                    textViewQuestion.alpha = 1.0f
//                }
//            }
//            true
//        }
        textViewQuestion.setClickableWithScale {
            if (presCounter < maxPresCounter) {
                if (presCounter == 0)
                    editText.setText("")
                editText.setText((editText.text.toString().trim() + text).trim())
                textViewAnswer.text = text
                textViewAnswer.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_text_answer, null)
                textViewAnswer.isClickable = true
                textViewAnswer.isFocusable = true
                textViewQuestion.text = null
                textViewQuestion.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_text_empty, null)
                textViewQuestion.isClickable = false
                textViewQuestion.isFocusable = false
                presCounter++
                val newPosition = (presCounter - 1) % viewParent2.columnCount
                viewParent2.removeView(textViewAnswer)
                viewParent2.addView(textViewAnswer, newPosition)
                if (presCounter == maxPresCounter)
                    checkAnswer()
            }
        }
        textViewAnswer.setClickableWithScale {
            if (presCounter > 0) {
                presCounter--
                val currentText = editText.text.toString()
                val charToRemove = textViewAnswer.text
                val newText = currentText.replace(charToRemove.toString(), "")
                editText.setText(newText)

                textViewQuestion.text = textViewAnswer.text
                textViewQuestion.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_text, null)
                textViewQuestion.isClickable = true
                textViewQuestion.isFocusable = true
                textViewAnswer.text = null
                textViewAnswer.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_empty_text, null)
                textViewAnswer.isClickable = false
                textViewAnswer.isFocusable = false
            }
        }
        if (presCounter == 0) {
            textViewAnswer.isClickable = false
            textViewAnswer.isFocusable = false
        }
        viewParent.addView(textViewQuestion)
        viewParent2.addView(textViewAnswer)
    }

    private fun checkAnswer() {

        if (binding.editText.text.toString() == textAnswer) {
            SharePreference.setIntPref(
                requireContext(),
                "LEVEL",
                level + 1
            )
            countDownTimer.cancel()
            router?.dialogLevelUp()
        } else {
            presCounter = 0
            Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()
            binding.ctnIncorrect.visibility = View.VISIBLE
            val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(200)
            }

            val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.music_fail)
            mediaPlayer.start()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.ctnIncorrect.visibility = View.GONE
            }, 2000)
            minus5sIncorrect()
            binding.editText.setText("")
            keys = shuffleArray(keys)
            binding.layoutQuestion.removeAllViews()
            binding.layoutAnswer.removeAllViews()
            for (key in keys) {
                addView(binding.layoutQuestion, key, binding.editText, binding.layoutAnswer)
            }

        }
    }

    private fun shuffleArray(ar: Array<String>): Array<String> {
        val rnd = Random()
        for (i in ar.size - 1 downTo 1) {
            val index = rnd.nextInt(i + 1)
            val a = ar[index]
            ar[index] = ar[i]
            ar[i] = a
        }
        return ar
    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}