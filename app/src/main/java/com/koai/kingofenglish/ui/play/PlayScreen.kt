package com.koai.kingofenglish.ui.play

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenPlayBinding
import com.koai.wordsdk.WordSdk
import java.util.Random


class PlayScreen : BaseScreen<ScreenPlayBinding, PlayRouter, MainNavigator>(R.layout.screen_play) {
    private var presCounter = 0
    private var maxPresCounter = 0
    private var keys: Array<String> = emptyArray()
    private lateinit var sentence: String
    private lateinit var textAnswer: String
    private lateinit var countDownTimer: CountDownTimer
    override fun initView(savedInstanceState: Bundle?, binding: ScreenPlayBinding) {
        textAnswer = "HAPPY"
        sentence = "HAPPY NEW YEAR TO ME"
        val words = sentence.split(" ").toTypedArray() // Chia câu thành mảng các từ
        keys = shuffleArray(words)
        maxPresCounter = keys.size
//        maxPresCounter = textAnswer.length
//        keys = textAnswer.toCharArray().map { it.toString() }.toTypedArray()
//        keys = shuffleArray(keys)
        binding.textViewQuestion.text = keys.joinToString("/")
        for (key in keys) {
            addView(binding.layoutQuestion, key, binding.editText, binding.layoutAnswer)
        }
        countDownTimer()
    }

    private fun countDownTimer() {
        val totalTimeInMillis: Long = 61 * 1000

        val intervalInMillis: Long = 5000
        countDownTimer = object : CountDownTimer(totalTimeInMillis, intervalInMillis) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                if (secondsRemaining <= 55) {
                    binding.txtTimeMinus.alpha = 1f
                    binding.txtTimeMinus.text = "-5"
                    binding.txtTimeMinus.animate().alpha(0f).setDuration(2000).start()
                }
                binding.txtTime.text = secondsRemaining.toString()

            }

            override fun onFinish() {
                binding.txtTime.text = "Done!"
            }
        }

        countDownTimer.start()
    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }


    @SuppressLint("SetTextI18n")
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

        textViewQuestion.setClickableWithScale {
            if (presCounter < maxPresCounter) {
                if (presCounter == 0)
                    editText.setText("")
                editText.setText((editText.text.toString().trim() + " " + text).trim())
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
                Toast.makeText(requireContext(), "$presCounter", Toast.LENGTH_SHORT).show()
                val newPosition = (presCounter - 1) % viewParent2.columnCount
                viewParent2.removeView(textViewAnswer)
                viewParent2.addView(textViewAnswer, newPosition)
                if (presCounter == maxPresCounter)
                    checkAnswer()
            }
        }
        viewParent.addView(textViewQuestion)
        viewParent2.addView(textViewAnswer)
    }

    private fun checkAnswer() {

        presCounter = 0
        if (binding.editText.text.toString() == sentence) {
            Toast.makeText(requireContext(), "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()
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