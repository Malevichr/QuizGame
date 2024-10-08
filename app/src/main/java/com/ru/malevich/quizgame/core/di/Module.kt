package com.ru.malevich.quizgame.core.di

import com.ru.malevich.quizgame.core.presentation.MyViewModel

interface Module<T : MyViewModel> {
    fun viewModel(): T
}