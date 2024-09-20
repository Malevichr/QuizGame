package com.ru.malevich.quizgame.gameover.di

import com.ru.malevich.quizgame.IntCache
import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.gameover.GameOverRepository
import com.ru.malevich.quizgame.gameover.GameOverViewModel

class ProvideGameOverViewModel(
    core: Core,
    nextLink: ProvideViewModel,
) : ProvideViewModel.AbstractChainLink(
    core,
    nextLink,
    GameOverViewModel::class.java
) {
    override fun module(): Module<out MyViewModel> =
        GameOverModule(core)
}

class GameOverModule(
    private val core: Core
) : Module<GameOverViewModel> {
    override fun viewModel(): GameOverViewModel =
        GameOverViewModel(
            GameOverRepository.Base(
                IntCache.Base(core.sharedPreferences, "corrects", 0),
                IntCache.Base(core.sharedPreferences, "incorrects", 0)
            ),
            core.clearViewModel
        )

}