package com.ru.malevich.quizgame

data class QuestionAndChoices(
    val question: String,
    val listOf: List<String>,
    val correctIndex: Int
) {

}