package com.ru.malevich.quizgame.game

import com.ru.malevich.quizgame.GameRepository
import com.ru.malevich.quizgame.views.choicebutton.ChoiceUiState

class GameViewModel(private val repository: GameRepository) {
    fun chooseFirst(): GameUiState {
        repository.saveUserChoice(0)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
            )
        )
    }

    fun chooseSecond(): GameUiState {
        repository.saveUserChoice(1)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
            )
        )
    }

    fun chooseThird(): GameUiState {
        repository.saveUserChoice(2)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
            )
        )
    }

    fun chooseForth(): GameUiState {
        repository.saveUserChoice(3)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
            )
        )
    }

    fun check(): GameUiState {
        val correctAndUserChoiceIndexes = repository.check()
        val listOfStates: List<ChoiceUiState> = List(4) { i ->
            when (i) {
                correctAndUserChoiceIndexes.correctIndex -> ChoiceUiState.Correct
                correctAndUserChoiceIndexes.userChoiceIndex -> ChoiceUiState.NotCorrect
                else -> ChoiceUiState.NotAvailableToChoose
            }
        }
        return GameUiState.AnswerCheckedState(
            listOfStates
        )
    }

    fun next(): GameUiState {
        return if (repository.isLastQuestion())
            GameUiState.Finish
        else {
            repository.next()
            init()
        }
    }

    fun init(firstRun: Boolean = true): GameUiState {
        return if (firstRun) {
            val data = repository.questionAndChoices()
            GameUiState.AskedQuestion(
                data.question,
                data.listOf
            )
        } else
            GameUiState.Empty
    }

}
