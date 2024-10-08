package com.ru.malevich.quizgame.core.di

import com.ru.malevich.quizgame.core.presentation.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel>)
}