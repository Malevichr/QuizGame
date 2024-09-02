package com.ru.malevich.quizgame.gameover

import com.ru.malevich.quizgame.IntCache

interface GameOverRepository {
    fun stats(): Pair<Int, Int>
    class Base(
        private val corrects: IntCache,
        private val incorrects: IntCache
    ) : GameOverRepository {
        override fun stats(): Pair<Int, Int> {
            val statsPair = Pair(corrects.read(), incorrects.read())
            corrects.save(0)
            incorrects.save(0)
            return statsPair
        }

    }
}