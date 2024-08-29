package com.ru.malevich.quizgame

import android.app.Application
import android.content.Context
import android.util.Log

class QuizApp : Application() {
    lateinit var viewModel: GameViewModel
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("quizAppData", Context.MODE_PRIVATE)
        viewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                IntCache.Base(sharedPreferences, "userChoiceIndex", -1)
            )
        )
        Log.d("mvlc", "application onCreate")
    }
}
