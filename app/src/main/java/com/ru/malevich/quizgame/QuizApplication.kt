package com.ru.malevich.quizgame

import com.ru.malevich.quizgame.di.ClearViewModel
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.ManageViewModels
import com.ru.malevich.quizgame.di.ProvideViewModel

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






