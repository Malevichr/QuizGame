package com.ru.malevich.quizgame.gameover

import com.ru.malevich.quizgame.core.MyViewModel
import com.ru.malevich.quizgame.di.ClearViewModel
import com.ru.malevich.quizgame.load.presentation.LoadUiState
import com.ru.malevich.quizgame.views.statstextview.StatsUiState


class GameOverViewModel(
    private val repository: GameOverRepository,
    private val clearViewModel: ClearViewModel,
) : MyViewModel<LoadUiState> {
    fun statsUiState(): StatsUiState {
        val statsPair = repository.stats()
        return StatsUiState.Base(statsPair.first, statsPair.second)
    }

    fun init(firstRun: Boolean = true): StatsUiState {
        if (firstRun) {
            val statsPair = repository.stats()
            repository.clearStats()
            return StatsUiState.Base(statsPair.first, statsPair.second)
        } else
            return StatsUiState.Empty
    }

    fun clear() {
        clearViewModel.clear(GameOverViewModel::class.java)
    }
}