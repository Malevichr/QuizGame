package com.ru.malevich.quizgame.load

interface LoadRepository {

    fun load(resultCallback: (LoadResult) -> Unit)
}
