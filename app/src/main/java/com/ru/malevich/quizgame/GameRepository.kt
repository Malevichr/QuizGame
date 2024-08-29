package com.ru.malevich.quizgame

interface GameRepository {
    fun questionAndChoices(): QuestionAndChoices

    fun saveUserChoice(index: Int)

    fun check(): CorrectAndUserChoiceIndexes

    fun next()

    class Base(
        private val index: IntCache,
        private val userChoiceIndex: IntCache,
        private val list: List<QuestionAndChoices> = listOf(
            QuestionAndChoices(
                question = "What color is this sky?",
                listOf("blue", "green", "red", "yellow"),
                correctIndex = 0
            ),
            QuestionAndChoices(
                question = "What color is the grass?",
                listOf("green", "blue", "red", "yellow"),
                correctIndex = 0
            ),
            QuestionAndChoices(
                question = "What color is the sun?",
                listOf("green", "blue", "red", "yellow"),
                correctIndex = 3
            ),
        )
    ) : GameRepository {

        override fun questionAndChoices(): QuestionAndChoices {
            return list[index.read()]
        }

        override fun saveUserChoice(index: Int) {
            userChoiceIndex.save(index)
        }

        override fun check(): CorrectAndUserChoiceIndexes {
            return CorrectAndUserChoiceIndexes(
                correctIndex = questionAndChoices().correctIndex,
                userChoiceIndex = userChoiceIndex.read()
            )
        }

        override fun next() {
            index.save((index.read() + 1) % list.size)
            userChoiceIndex.save(-1)
        }
    }
}

