package com.ru.malevich.quizgame.game.di

import com.ru.malevich.quizgame.IntCache
import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.game.GameRepository
import com.ru.malevich.quizgame.game.GameViewModel

class GameModule(
    private val core: Core
) : Module<GameViewModel> {
    override fun viewModel(): GameViewModel =
        GameViewModel(
            GameRepository.Base(
                IntCache.Base(core.sharedPreferences, "indexKey", 0),
                IntCache.Base(core.sharedPreferences, "userChoiceIndex", -1),
                IntCache.Base(core.sharedPreferences, "corrects", 0),
                IntCache.Base(core.sharedPreferences, "incorrects", 0)
            ),
            clearViewModel = core.clearViewModel
        )
}

class ProvideGameViewModel(core: Core, nextLink: ProvideViewModel) :
    ProvideViewModel.AbstractChainLink(
        core,
        nextLink,
        GameViewModel::class.java
    ) {
    override fun module(): Module<out MyViewModel> =
        GameModule(core)
}