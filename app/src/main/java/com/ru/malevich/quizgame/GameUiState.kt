package com.ru.malevich.quizgame

import com.ru.malevich.quizgame.databinding.ActivityMainBinding

interface GameUiState {
    fun update(binding: ActivityMainBinding)
    data class AskedQuestion(
        val question: String,
        val choices: List<String>
    ) : GameUiState {
        override fun update(binding: ActivityMainBinding) {

        }

    }

    data class ChoiceMade(
        val question: String,
        val choices: List<ChoiceUiState>
    ) : GameUiState {
        override fun update(binding: ActivityMainBinding) {

        }

    }

    data class AnswerCheckedState(val question: String, val choices: List<ChoiceUiState>) :
        GameUiState {
        override fun update(binding: ActivityMainBinding) {

        }

    }


}

interface ChoiceUiState {
    data class NotAvailableToChoose(val text: String) : ChoiceUiState {

    }

    data class AvailableToChoose(val text: String) : ChoiceUiState {

    }

    data class Correct(val text: String) : ChoiceUiState {

    }

    data class NotCorrect(val text: String) : ChoiceUiState {

    }

}