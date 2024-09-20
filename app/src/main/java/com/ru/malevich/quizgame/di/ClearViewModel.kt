package com.ru.malevich.quizgame.di

import com.ru.malevich.quizgame.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel>)
}