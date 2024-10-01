package com.ru.malevich.quizgame.game.di

import com.ru.malevich.quizgame.IntCache
import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.StringCache
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.game.GameRepository
import com.ru.malevich.quizgame.game.GameViewModel
import com.ru.malevich.quizgame.load.data.ParseQuestionAndChoices
import com.ru.malevich.quizgame.load.data.QuizResponse

class GameModule(
    private val core: Core
) : Module<GameViewModel> {
    override fun viewModel(): GameViewModel {

        val defaultResponseData = QuizResponse(-1, emptyList())
        val defaultGsonString = core.gson.toJson(defaultResponseData)
        return if (core.runUiTests)
            GameViewModel(
                GameRepository.Base(
                    index = IntCache.Base(core.sharedPreferences, "indexKey", 0),
                    userChoiceIndex = IntCache.Base(core.sharedPreferences, "userChoiceIndex", -1),
                    corrects = IntCache.Base(core.sharedPreferences, "corrects", 0),
                    incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0),

                    ),
                clearViewModel = core.clearViewModel
            )
        else
            GameViewModel(
                GameRepository.Base(
                    index = IntCache.Base(core.sharedPreferences, "indexKey", 0),
                    userChoiceIndex = IntCache.Base(core.sharedPreferences, "userChoiceIndex", -1),
                    corrects = IntCache.Base(core.sharedPreferences, "corrects", 0),
                    incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0),
                    dataCache = StringCache.Base(
                        core.sharedPreferences,
                        "question_data",
                        defaultGsonString
                    ),
                    parseQuestionAndChoices = ParseQuestionAndChoices.Base(core.gson)
                ),
            clearViewModel = core.clearViewModel
        )
    }
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