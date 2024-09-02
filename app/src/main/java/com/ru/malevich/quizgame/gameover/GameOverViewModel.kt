package com.ru.malevich.quizgame.gameover

import com.ru.malevich.quizgame.views.statstextview.StatsUiState


class GameOverViewModel(
    private val repository: GameOverRepository
) {
    fun statsUiState(): StatsUiState {
        val statsPair = repository.stats()
        return StatsUiState.Base(statsPair.first, statsPair.second)
    }
}