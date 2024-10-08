package com.ru.malevich.quizgame.main.presentation

import com.ru.malevich.quizgame.core.data.IntCache
import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.core.presentation.Screen
import com.ru.malevich.quizgame.game.presentation.GameScreen
import com.ru.malevich.quizgame.load.presentation.LoadScreen

class MainViewModel(
    private val index: IntCache,
    private val size: Int
) : MyViewModel {
    fun firstScreen(firstRun: Boolean): Screen {
        return if (firstRun) {
            if (index.read() == size)
                LoadScreen
            else
                GameScreen
        } else
            Screen.Empty
    }
}