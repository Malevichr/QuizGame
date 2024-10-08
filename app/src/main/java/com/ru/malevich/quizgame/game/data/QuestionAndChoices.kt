package com.ru.malevich.quizgame.game.data

data class QuestionAndChoices(
    val question: String,
    val listOf: List<String>,
    val correctIndex: Int
)