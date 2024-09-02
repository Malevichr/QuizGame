package com.ru.malevich.quizgame.gameover

import com.ru.malevich.quizgame.views.statstextview.StatsUiState


class GameOverViewModel(
    repository: GameOverRepository
) {
    fun statsUiState() = StatsUiState.Base(1, 1)
}