package com.ru.malevich.quizgame.di

import com.ru.malevich.quizgame.MyViewModel

interface Module<T : MyViewModel> {
    fun viewModel(): T
}