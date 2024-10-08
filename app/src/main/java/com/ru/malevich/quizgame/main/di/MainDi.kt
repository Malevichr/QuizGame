package com.ru.malevich.quizgame.main.di

import com.ru.malevich.quizgame.core.data.IntCache
import com.ru.malevich.quizgame.core.di.Core
import com.ru.malevich.quizgame.core.di.Module
import com.ru.malevich.quizgame.core.di.ProvideViewModel
import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.main.presentation.MainViewModel

class ProvideMainViewModel(
    core: Core,
    nextLink: ProvideViewModel,
) : ProvideViewModel.AbstractChainLink(
    core,
    nextLink,
    MainViewModel::class.java
) {
    override fun module(): Module<out MyViewModel> =
        MainModule(core)
}

class MainModule(
    private val core: Core
) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel = MainViewModel(
        index = IntCache.Base(core.sharedPreferences, "indexKey", core.size),
        core.size
    )
}