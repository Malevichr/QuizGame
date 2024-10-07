package com.ru.malevich.quizgame.game.di

import com.ru.malevich.quizgame.core.IntCache
import com.ru.malevich.quizgame.core.MyViewModel
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.game.GameRepository
import com.ru.malevich.quizgame.game.GameUiObservable
import com.ru.malevich.quizgame.game.GameViewModel

class GameModule(
    private val core: Core
) : Module<GameViewModel> {
    override fun viewModel(): GameViewModel {
        return if (core.runUiTests)
            GameViewModel(
                repository = GameRepository.Fake(
                    index = IntCache.Base(core.sharedPreferences, "indexKey", 0),
                    userChoiceIndex = IntCache.Base(core.sharedPreferences, "userChoiceIndex", -1),
                    corrects = IntCache.Base(core.sharedPreferences, "corrects", 0),
                    incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0),
                    ),
                clearViewModel = core.clearViewModel,
                observable = GameUiObservable.Base(),
                runAsync = core.runAsync
            )
        else
            GameViewModel(
                repository = GameRepository.Base(
                    index = IntCache.Base(core.sharedPreferences, "indexKey", 0),
                    userChoiceIndex = IntCache.Base(core.sharedPreferences, "userChoiceIndex", -1),
                    corrects = IntCache.Base(core.sharedPreferences, "corrects", 0),
                    incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0),
                    dao = core.cacheModule.dao(),
                    clearDatabase = core.cacheModule.clearDatabase(),
                    size = core.size
                ),
                clearViewModel = core.clearViewModel,
                observable = GameUiObservable.Base(),
                runAsync = core.runAsync
            )
    }
}

class ProvideGameViewModel(core: Core, nextLink: ProvideViewModel) :
    ProvideViewModel.AbstractChainLink(
        core,
        nextLink,
        GameViewModel::class.java
    ) {
    override fun module(): Module<out MyViewModel<*>> =
        GameModule(core)
}