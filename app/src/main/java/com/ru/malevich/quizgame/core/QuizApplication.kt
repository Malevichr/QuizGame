package com.ru.malevich.quizgame.core

import com.ru.malevich.quizgame.core.di.ClearViewModel
import com.ru.malevich.quizgame.core.di.Core
import com.ru.malevich.quizgame.core.di.ManageViewModels
import com.ru.malevich.quizgame.core.di.ProvideViewModel
import com.ru.malevich.quizgame.core.presentation.LoggedApplication
import com.ru.malevich.quizgame.core.presentation.MyViewModel

class QuizApplication : LoggedApplication(), ProvideViewModel {
    private lateinit var factory: ManageViewModels

    override fun onCreate() {
        super.onCreate()
        val clearViewModel = object : ClearViewModel {
            override fun clear(viewModelClass: Class<out MyViewModel>) {
                factory.clear(viewModelClass)
            }
        }
        val core = Core(this, clearViewModel)
        val make = ProvideViewModel.Make(core)
        factory = ManageViewModels.Factory(make)
    }

    override fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T =
        factory.makeViewModel(clazz)

}






