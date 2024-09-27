package com.ru.malevich.quizgame.load.di

import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.StringCache
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.load.data.LoadRepository
import com.ru.malevich.quizgame.load.data.ParseQuestionAndChoices
import com.ru.malevich.quizgame.load.data.Response
import com.ru.malevich.quizgame.load.presentation.LoadViewModel
import com.ru.malevich.quizgame.load.presentation.UiObservable

class LoadModule(private val core: Core) : Module<LoadViewModel> {
    override fun viewModel(): LoadViewModel {
        val defaultResponseData = Response(-1, emptyList())
        val defaultGsonString = core.gson.toJson(defaultResponseData)

        return LoadViewModel(
            LoadRepository.Base(
                StringCache.Base(core.sharedPreferences, "question_data", defaultGsonString),
                parseQuestionAndChoices = ParseQuestionAndChoices.Base(core.gson)
            ),
            observable = UiObservable.Base()
        )
    }


}

class ProvideLoadViewModel(core: Core, nextLink: ProvideViewModel) :
    ProvideViewModel.AbstractChainLink(
        core,
        nextLink,
        LoadViewModel::class.java
    ) {
    override fun module(): Module<out MyViewModel> =
        LoadModule(core)
}