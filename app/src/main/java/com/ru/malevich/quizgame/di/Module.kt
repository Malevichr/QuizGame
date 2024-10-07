package com.ru.malevich.quizgame.di

import com.ru.malevich.quizgame.core.MyViewModel

interface Module<T : MyViewModel<*>> {
    fun viewModel(): T
}