package com.ru.malevich.quizgame.gameover

interface GameOverRepository {
    fun stats(): Pair<Int, Int>
}