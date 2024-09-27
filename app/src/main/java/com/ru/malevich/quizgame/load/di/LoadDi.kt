package com.ru.malevich.quizgame.load.di

import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.load.LoadRepository
import com.ru.malevich.quizgame.load.LoadViewModel
import com.ru.malevich.quizgame.load.UiObservable

class LoadModule : Module<LoadViewModel> {
    override fun viewModel(): LoadViewModel =
        LoadViewModel(
            LoadRepository.Base(),
            observable = UiObservable.Base()
        )
}

class ProvideLoadViewModel(core: Core, nextLink: ProvideViewModel) :
    ProvideViewModel.AbstractChainLink(
        core,
        nextLink,
        LoadViewModel::class.java
    ) {
    override fun module(): Module<out MyViewModel> =
        LoadModule()
}