package com.ru.malevich.quizgame.di

import com.ru.malevich.quizgame.MyViewModel

interface ManageViewModels : ProvideViewModel, ClearViewModel {
    class Factory(
        private val make: ProvideViewModel
    ) : ManageViewModels {
        private val viewModelMap = mutableMapOf<Class<out MyViewModel>, MyViewModel?>()

        override fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T {
            return if (viewModelMap[clazz] == null) {
                val viewModel = make.makeViewModel(clazz)
                viewModelMap[clazz] = viewModel
                viewModel
            } else
                viewModelMap[clazz] as T

        }

        override fun clear(viewModelClass: Class<out MyViewModel>) {
            viewModelMap[viewModelClass] = null
        }
    }
}