package com.ru.malevich.quizgame.core.presentation

import com.ru.malevich.quizgame.game.presentation.GameScreen
import com.ru.malevich.quizgame.game.presentation.NavigateToGame
import com.ru.malevich.quizgame.gameover.presentation.GameOverScreen
import com.ru.malevich.quizgame.gameover.presentation.NavigateToGameOver
import com.ru.malevich.quizgame.load.presentation.LoadScreen
import com.ru.malevich.quizgame.load.presentation.NavigateToLoad

interface Navigate : NavigateToGame, NavigateToGameOver, NavigateToLoad {
    fun navigate(screen: Screen)
    override fun navigateToGame() {
        navigate(GameScreen)
    }

    override fun navigateToGameOver() {
        navigate(GameOverScreen)
    }

    override fun navigateToLoad() {
        navigate(LoadScreen)
    }
}

