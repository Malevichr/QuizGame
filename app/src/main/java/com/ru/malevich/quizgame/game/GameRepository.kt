package com.ru.malevich.quizgame.game

import com.ru.malevich.quizgame.IntCache

interface GameRepository {
    fun questionAndChoices(): QuestionAndChoices

    fun saveUserChoice(index: Int)

    fun check(): CorrectAndUserChoiceIndexes

    fun next()

    fun isLastQuestion(): Boolean

    fun clearProgress()

    class Base(
        private val index: IntCache,
        private val userChoiceIndex: IntCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val list: List<QuestionAndChoices> = listOf(
            QuestionAndChoices(
                question = "What color is the sky?",
                listOf("blue", "green", "red", "yellow"),
                correctIndex = 0
            ),
            QuestionAndChoices(
                question = "What color is the grass?",
                listOf("green", "blue", "red", "yellow"),
                correctIndex = 0
            ),
//            QuestionAndChoices(
//                question = "What color is the sun?",
//                listOf("yellow", "green", "blue", "red"),
//                correctIndex = 0
//            ),
        )
    ) : GameRepository {

        override fun questionAndChoices(): QuestionAndChoices {
            return list[index.read()]
        }

        override fun saveUserChoice(index: Int) {
            userChoiceIndex.save(index)
        }

        override fun check(): CorrectAndUserChoiceIndexes {
            val correctIndex = questionAndChoices().correctIndex
            val userIndex = userChoiceIndex.read()
            if (correctIndex == userIndex)
                corrects.increment()
            else
                incorrects.increment()
            return CorrectAndUserChoiceIndexes(
                correctIndex,
                userIndex
            )
        }

        override fun next() {
            index.save((index.read() + 1) % list.size)
            userChoiceIndex.save(-1)
        }

        override fun isLastQuestion(): Boolean =
            index.read() + 1 == list.size

        override fun clearProgress() {
            index.default()
            userChoiceIndex.default()
        }
    }

}

