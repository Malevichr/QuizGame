package com.ru.malevich.quizgame.presentation

import com.ru.malevich.quizgame.game.GameScreen
import com.ru.malevich.quizgame.game.NavigateToGame
import com.ru.malevich.quizgame.gameover.GameOverScreen
import com.ru.malevich.quizgame.gameover.NavigateToGameOver
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

