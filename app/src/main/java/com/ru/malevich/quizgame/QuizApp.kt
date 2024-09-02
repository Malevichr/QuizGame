package com.ru.malevich.quizgame

import android.app.Application
import android.content.Context
import android.util.Log
import com.ru.malevich.quizgame.game.GameViewModel
import com.ru.malevich.quizgame.gameover.GameOverViewModel

class QuizApp : Application() {
    lateinit var gameViewModel: GameViewModel
    lateinit var gameOverViewModel: GameOverViewModel
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("quizAppData", Context.MODE_PRIVATE)
        gameViewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                IntCache.Base(sharedPreferences, "userChoiceIndex", -1)
            )
        )
        gameOverViewModel = GameOverViewModel()
        Log.d("mvlc", "application onCreate")
    }
}
