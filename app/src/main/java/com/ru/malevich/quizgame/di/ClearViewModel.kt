package com.ru.malevich.quizgame.di

import com.ru.malevich.quizgame.core.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel<*>>)
}