package com.ru.malevich.quizgame

interface GameRepository {
    fun questionAndChoices(): QuestionAndChoices

    fun saveUserChoice(index: Int)

    fun check(): CorrectAndUserChoiceIndexes

    fun next()

    class Base(
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
        )
    ) : GameRepository {


        private var index: Int = 0

        override fun questionAndChoices(): QuestionAndChoices {
            return list[index]
        }

        private var userChoiceIndex = -1
        override fun saveUserChoice(index: Int) {
            userChoiceIndex = index
        }

        override fun check(): CorrectAndUserChoiceIndexes {
            return CorrectAndUserChoiceIndexes(
                correctIndex = questionAndChoices().correctIndex,
                userChoiceIndex = userChoiceIndex
            )
        }

        override fun next() {
            index = ++index % list.size
            userChoiceIndex = -1
        }
    }
}

